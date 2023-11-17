package by.alexandr7035.banking.ui.feature_cards.dialog_card_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.core.EnterScreenEffect
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCard
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardPickerDialog(
    viewModel: CardPickerViewModel = koinViewModel(),
    onDismissRequest: (selectedCardNumber: String?) -> Unit = {},
    defaultSelectedCard: String? = null
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val dialogState = rememberModalBottomSheetState()

    val selectedCardNumber = rememberSaveable<MutableState<String?>> {
        mutableStateOf(null)
    }

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest(selectedCardNumber.value)
        },
        sheetState = dialogState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        CardPickerDialog_Ui(
            state = state,
            onDismissRequest = {
                scope.launch {
                    selectedCardNumber.value = it
                    dialogState.hide()
                    onDismissRequest(selectedCardNumber.value)
                }
            },
            onRequestExpand = {
                scope.launch {
                    dialogState.expand()
                }
            },
            onRequestLoad = {
                viewModel.emitIntent(CardPickerIntent.LoadCards(defaultSelectedCard))
            }
        )
    }

    EnterScreenEffect {
        viewModel.emitIntent(CardPickerIntent.LoadCards(defaultSelectedCard))
    }
}

@Composable
private fun CardPickerDialog_Ui(
    state: CardPickerState,
    onDismissRequest: (selectedCardNumber: String?) -> Unit = {},
    onRequestExpand: () -> Unit = {},
    onRequestLoad: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.choose_card),
            style = TextStyle(
                fontSize = 18.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF262626),
                textAlign = TextAlign.Center,
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        when {
            state.isLoading -> {
                Box(
                    modifier = Modifier.padding(56.dp),
                    contentAlignment = Alignment.Center
                ) {
                    DotsProgressIndicator()
                }
            }

            state.cards != null -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 48.dp
                    )
                ) {
                    if (state.cards.isNotEmpty()) {
                        items(state.cards) { card ->
                            PaymentCard(
                                cardUi = card,
                                onCLick = {
                                    onDismissRequest(card.id)
                                },
                                modifier = Modifier,
                                isSelected = card.id == state.selectedCardId
                            )
                        }
                    } else {
                        item {
                            Text(
                                text = "You have no added cards",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontFamily = primaryFontFamily,
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF333333),
                                    textAlign = TextAlign.Center,
                                ),
                                modifier = Modifier.padding(
                                    vertical = 56.dp,
                                    horizontal = 24.dp
                                )
                            )
                        }
                    }
                }
            }

            state.error != null -> {
                ErrorFullScreen(
                    error = state.error,
                    onRetry = {
                        onRequestLoad()
                    },
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                )

                LaunchedEffect(Unit) {
                    onRequestExpand()
                }
            }
        }
    }

}

@Preview
@Composable
fun CardPickerDialog_Preview() {
    ScreenPreview {
        CardPickerDialog_Ui(
            state = CardPickerState(
                cards = List(4) { CardUi.mock() }
            ))
    }
}

@Preview
@Composable
fun CardPickerDialog_Loading_Preview() {
    ScreenPreview {
        CardPickerDialog_Ui(
            state = CardPickerState(
                isLoading = true,
                cards = emptyList()
            )
        )
    }
}

@Preview
@Composable
fun CardPickerDialog_Error_Preview() {
    ScreenPreview {
        CardPickerDialog_Ui(
            state = CardPickerState(
                isLoading = false,
                error = UiText.DynamicString("Failed to load cards")
            )
        )
    }
}

@Preview
@Composable
fun CardPickerDialog_NoCards_Preview() {
    ScreenPreview {
        CardPickerDialog_Ui(
            state = CardPickerState(
                isLoading = false,
                cards = emptyList()
            )
        )
    }
}

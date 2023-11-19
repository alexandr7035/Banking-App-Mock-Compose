package by.alexandr7035.banking.ui.feature_contacts.dialog_contact_picker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import by.alexandr7035.banking.ui.core.effects.EnterScreenEffect
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_contacts.components.ContactCard
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactPickerDialog(
    viewModel: ContactPickerDialogViewModel = koinViewModel(),
    onDismissRequest: (selectedContactId: Long?) -> Unit = {},
    defaultSelectedContactId: Long? = null
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val dialogState = rememberModalBottomSheetState()

    val selectedContactId = rememberSaveable<MutableState<Long?>> {
        mutableStateOf(null)
    }

    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = {
            onDismissRequest(selectedContactId.value)
        },
        sheetState = dialogState,
        containerColor = MaterialTheme.colorScheme.background
    ) {
        ContactPickerDialog_Ui(
            state = state,
            onDismissRequest = {
                scope.launch {
                    selectedContactId.value = it
                    dialogState.hide()
                    onDismissRequest(selectedContactId.value)
                }
            },
            onRequestExpand = {
                scope.launch {
                    dialogState.expand()
                }
            }
        )
    }

    EnterScreenEffect {
        viewModel.emitIntent(ContactPickerDialogIntent.LoadContacts(defaultSelectedContactId))
    }
}

@Composable
private fun ContactPickerDialog_Ui(
    state: ContactPickerDialogState,
    onDismissRequest: (selectedContactId: Long?) -> Unit = {},
    onRequestExpand: () -> Unit = {},
    onRequestLoad: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(R.string.choose_contact),
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

            state.contacts != null -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(
                        start = 24.dp,
                        end = 24.dp,
                        bottom = 48.dp
                    )
                ) {
                    if (state.contacts.isNotEmpty()) {
                        items(state.contacts) { contact ->
                            ContactCard(
                                contact = contact,
                                isSelected = contact.id == state.selectedContactId,
                                onCLick = {
                                    onDismissRequest(contact.id)
                                }
                            )
                        }
                    } else {
                        item {
                            Text(
                                text = stringResource(R.string.you_have_no_contacts),
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
fun ContactPickerDialog_Preview() {
    ScreenPreview {
        ContactPickerDialog_Ui(
            state = ContactPickerDialogState(
                contacts = List(4) { ContactUi.mock() }
            ))
    }
}

@Preview
@Composable
fun ContactPickerDialog_Loading_Preview() {
    ScreenPreview {
        ContactPickerDialog_Ui(
            state = ContactPickerDialogState(
                isLoading = true,
                contacts = emptyList()
            )
        )
    }
}

@Preview
@Composable
fun ContactPickerDialog_Error_Preview() {
    ScreenPreview {
        ContactPickerDialog_Ui(
            state = ContactPickerDialogState(
                isLoading = false,
                error = UiText.DynamicString("Failed to load contacts")
            )
        )
    }
}

@Preview
@Composable
fun ContactPickerDialog_NoContacts_Preview() {
    ScreenPreview {
        ContactPickerDialog_Ui(
            state = ContactPickerDialogState(
                isLoading = false,
                contacts = emptyList()
            )
        )
    }
}
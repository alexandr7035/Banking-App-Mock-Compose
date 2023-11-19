package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import android.annotation.SuppressLint
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.app_host.host_utils.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryButton
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_account.components.account_actions.AccountAction
import by.alexandr7035.banking.ui.feature_account.components.account_actions.AccountActionRow
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCard
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCardSkeleton
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun CardDetailsScreen(
    viewModel: CardDetailsViewModel = koinViewModel(),
    cardId: String,
    onBack: () -> Unit,
    onAccountAction: (AccountAction) -> Unit,
) {

    val ctx = LocalContext.current
    val snackbarState = LocalScopedSnackbarState.current

    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(topBar = {
        SecondaryToolBar(
            onBack = onBack,
            title = UiText.StringResource(R.string.card_details),
            actions = {
                val card = state.card
                card?.let {
                    IconButton(
                        onClick = {
                            viewModel.emitIntent(
                                CardDetailsIntent.SetCardAsPrimary(
                                    cardId = card.id,
                                    makePrimary = !card.isPrimary
                                )
                            )
                        },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_bookmark_filled),
                            contentDescription = stringResource(R.string.set_card_as_default),
                            tint = if (card.isPrimary) MaterialTheme.colorScheme.primary else Color.LightGray,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
        )
    }) { pv ->
        BoxWithConstraints(
            Modifier.padding(
                top = pv.calculateTopPadding(),
                bottom = pv.calculateBottomPadding()
            )
        ) {
            when {
                state.showCardSkeleton -> CardDetailsScreen_Skeleton()

                state.card != null -> {
                    CardDetailsScreen_Ui(
                        cardUi = state.card,
                        onIntent = { viewModel.emitIntent(it) },
                        onAccountAction = onAccountAction
                    )

                    if (state.showLoading) {
                        FullscreenProgressBar()
                    }

                    if (state.showDeleteCardDialog) {
                        ConfirmDeleteCardDialog(onDismiss = {
                            viewModel.emitIntent(
                                CardDetailsIntent.ToggleDeleteCardDialog(
                                    isDialogShown = false
                                )
                            )
                        }, onConfirmDelete = {
                            viewModel.emitIntent(CardDetailsIntent.ConfirmDeleteCard)
                        })
                    }
                }

                state.error != null -> {
                    ErrorFullScreen(
                        error = state.error,
                        onRetry = { viewModel.emitIntent(CardDetailsIntent.EnterScreen(cardId)) }
                    )
                }
            }

            EventEffect(
                event = state.cardDeletedResultEvent,
                onConsumed = viewModel::consumeDeleteResultEvent,
            ) { result ->
                when (result) {
                    is OperationResult.Success -> {
                        snackbarState.show(
                            message = "Card deleted", snackBarMode = SnackBarMode.Positive
                        )

                        onBack.invoke()
                    }

                    is OperationResult.Failure -> {
                        snackbarState.show(
                            message = result.error.errorType.asUiTextError().asString(ctx), snackBarMode = SnackBarMode.Negative
                        )
                    }
                }

            }

            EventEffect(
                event = state.setCardAsPrimaryEvent,
                onConsumed = viewModel::consumeSetCardAsDefaultEvent,
            ) { result ->
                when (result) {
                    is OperationResult.Success -> {}

                    is OperationResult.Failure -> {
                        snackbarState.show(
                            message = result.error.errorType.asUiTextError().asString(ctx), snackBarMode = SnackBarMode.Negative
                        )
                    }
                }

            }

            LaunchedEffect(Unit) {
                viewModel.emitIntent(CardDetailsIntent.EnterScreen(cardId))
            }
        }
    }
}

@Composable
private fun CardDetailsScreen_Ui(
    modifier: Modifier = Modifier,
    cardUi: CardUi,
    onIntent: (intent: CardDetailsIntent) -> Unit = {},
    onAccountAction: (AccountAction) -> Unit = {}
) {
    Column(
        modifier = modifier.then(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    vertical = 16.dp, horizontal = 24.dp
                ),
        ), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            PaymentCard(cardUi = cardUi)
            Spacer(modifier = Modifier.height(8.dp))
        }

        AccountActionRow(
            modifier = Modifier.fillMaxWidth(), onActionClick = onAccountAction
        )

        Text(
            text = stringResource(R.string.billing_address), style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF100D40),
            )
        )

        Text(
            text = cardUi.addressFirstLine, style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
            )
        )

        if (cardUi.addressSecondLine != null) {
            Text(
                text = cardUi.addressSecondLine, style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                )
            )
        }

        Text(
            text = stringResource(R.string.added_on), style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF100D40),
            )
        )

        Text(
            text = cardUi.dateAdded, style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
            )
        )

        Spacer(Modifier.weight(1f))

        SecondaryButton(
            onClick = {
                onIntent.invoke(CardDetailsIntent.ToggleDeleteCardDialog(isDialogShown = true))
            }, modifier = Modifier.fillMaxWidth(), text = stringResource(id = R.string.remove_card)
        )
    }
}

@Composable
private fun CardDetailsScreen_Skeleton() {
    Column(
        modifier = Modifier.padding(
            vertical = 16.dp,
            horizontal = 24.dp,
        ), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            PaymentCardSkeleton()
        }

        repeat(2) {
            SkeletonShape(
                modifier = Modifier
                    .height(24.dp)
                    .width(100.dp)
            )

            SkeletonShape(
                modifier = Modifier
                    .height(20.dp)
                    .width(160.dp)
            )
        }
    }
}

@Composable
@Preview
fun CardDetailsScreen_Preview() {
    ScreenPreview {
        CardDetailsScreen_Ui(cardUi = CardUi.mock())
    }
}

@Composable
@Preview(widthDp = 280, heightDp = 400)
fun CardDetailsScreen_Preview_Small() {
    ScreenPreview {
        CardDetailsScreen_Ui(cardUi = CardUi.mock())
    }
}

@Composable
@Preview
fun CardDetailsScreen_Skeleton_Preview() {
    ScreenPreview {
        CardDetailsScreen_Skeleton()
    }
}
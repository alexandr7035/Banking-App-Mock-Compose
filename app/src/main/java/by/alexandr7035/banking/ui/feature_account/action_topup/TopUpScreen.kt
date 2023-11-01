package by.alexandr7035.banking.ui.feature_account.action_topup

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.account.model.BalanceValue
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.header.ScreenHeader
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_account.components.BalanceGridPicker
import by.alexandr7035.banking.ui.feature_cards.components.PanelCardSelector
import org.koin.androidx.compose.koinViewModel

@Composable
fun TopUpScreen(
    viewModel: TopUpScreenViewModel = koinViewModel()
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    TopUpScreen_Ui(
        state = state,
        onIntent = { viewModel.emitIntent(it) }
    )
}

@Composable
fun TopUpScreen_Ui(
    state: TopUpScreenState,
    onIntent: (TopUpScreenIntent) -> Unit = {}
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenHeader(
                toolbar = {
                    SecondaryToolBar(
                        onBack = { },
                        title = UiText.StringResource(R.string.top_up),
                        containerColor = Color.Transparent,
                        contentColor = Color.White,
                        modifier = Modifier
                            .wrapContentHeight()
                            .padding(top = 16.dp)
                    )
                }
            ) {
                PanelCardSelector(selectedCard = state.selectedCard)
            }

            Spacer(Modifier.height(24.dp))

            BalanceGridPicker(
                proposedValues = state.proposedValues,
                selectedValue = state.selectedAmount,
                onValueSelected = {
                    onIntent(TopUpScreenIntent.UpdateSelectedValue(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            )
        }
    }
}

@Composable
@Preview
fun TopUpScreen_Preview() {
    ScreenPreview {
        TopUpScreen_Ui(
            state = TopUpScreenState(
                proposedValues = setOf(100, 200, 300, 400, 500, 600).map {
                    BalanceValue.LongBalance(it.toLong())
                }.toSet()
            )
        )
    }
}
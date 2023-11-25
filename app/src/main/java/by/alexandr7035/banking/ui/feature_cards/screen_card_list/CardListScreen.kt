package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DashedButton
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCard
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCardSkeleton
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardListScreen(
    viewModel: CardListViewModel = koinViewModel(),
    onBack: () -> Unit,
    onAddCard: () -> Unit,
    onCardDetails: (cardNumber: String) -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            SecondaryToolBar(
                onBack = onBack,
                title = UiText.StringResource(R.string.your_cards)
            )
        },
        floatingActionButton = {
            AnimatedVisibility(state.floatingAddCardShown) {
                FloatingActionButton(
                    onClick = {
                        onAddCard.invoke()
                    },
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = stringResource(id = R.string.add_a_card)
                    )
                }
            }
        }
    ) { pv ->

        Box(
            Modifier.padding(
                top = pv.calculateTopPadding(),
                bottom = pv.calculateBottomPadding()
            )
        ) {
            when {
                state.isLoading -> CardListScreen_Skeleton()

                state.error != null -> {
                    ErrorFullScreen(
                        error = state.error,
                        onRetry = {
                            viewModel.emitIntent(CardListIntent.EnterScreen)
                        }
                    )
                }

                else -> {
                    CardListScreen_Ui(
                        cards = state.cards,
                        onAddCard = { onAddCard.invoke() },
                        onCardDetails = { onCardDetails.invoke(it) },
                        onToggleFab = {
                            viewModel.emitIntent(CardListIntent.ToggleFloatingAddCardButton(it))
                        }
                    )
                }
            }
        }

        LaunchedEffect(Unit) {
            viewModel.emitIntent(CardListIntent.EnterScreen)
        }
    }
}

@Composable
fun CardListScreen_Ui(
    cards: List<CardUi>,
    onAddCard: () -> Unit = {},
    onCardDetails: (cardId: String) -> Unit = {},
    onToggleFab: (isVisible: Boolean) -> Unit = {}
) {

    val listState = rememberLazyListState()

    val shouldShowFab by remember {
        derivedStateOf {
            listState.canScrollForward
        }
    }

    LaunchedEffect(shouldShowFab) {
        onToggleFab.invoke(shouldShowFab)
    }

    LazyColumn(
        modifier = Modifier
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp,
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = listState
    ) {

        if (cards.isNotEmpty()) {
            itemsIndexed(
                items = cards,
            ) { _, card ->
                PaymentCard(
                    cardUi = card,
                    onCLick = { onCardDetails.invoke(card.id) }
                )
            }

            item() {
                DashedButton(
                    onClick = { onAddCard.invoke() },
                    modifier = Modifier.size(300.dp, 200.dp),
                    text = stringResource(id = R.string.add_a_card),
                    icon = painterResource(id = R.drawable.ic_plus)
                )
            }

        } else {
            item() {
                DashedButton(
                    onClick = { onAddCard.invoke() },
                    modifier = Modifier
                        .width(300.dp)
                        .height(200.dp),
                    text = stringResource(id = R.string.add_a_card),
                    icon = painterResource(id = R.drawable.ic_plus)
                )
            }
        }
    }
}

@Composable
private fun CardListScreen_Skeleton() {
    Column(
        modifier = Modifier
            .padding(
                vertical = 16.dp,
                horizontal = 24.dp,
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(2) {
            PaymentCardSkeleton()
        }
    }
}


@Composable
@Preview
fun CardListScreen_Preview() {
    ScreenPreview {
        CardListScreen_Ui(
            List(3) {
                CardUi.mock()
            }
        )
    }
}

@Composable
@Preview
fun CardListScreen_Empty_Preview() {
    ScreenPreview {
        CardListScreen_Ui(cards = emptyList())
    }
}

@Composable
@Preview
fun CardListScreen_Skeleton_Preview() {
    ScreenPreview {
        CardListScreen_Skeleton()
    }
}
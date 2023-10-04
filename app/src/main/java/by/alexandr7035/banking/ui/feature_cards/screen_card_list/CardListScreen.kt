package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DashedButton
import by.alexandr7035.banking.ui.components.ErrorFullScreen
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.extensions.showToast
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCard
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun CardListScreen(
    viewModel: CardListViewModel = koinViewModel(),
    onBack: () -> Unit,
    onAddCard: () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            ToolBar(onBack)
        }
    ) { pv ->

        Box(
            Modifier.padding(
                top = pv.calculateTopPadding(),
                bottom = pv.calculateBottomPadding()
            )
        ) {
            when (state) {
                is CardListState.Loading -> CardListScreen_Skeleton()

                is CardListState.Success -> {
                    CardListScreen_Ui(
                        cards = List(2) {
                            CardUi.mock()
                        },
                        onAddCard = { onAddCard.invoke() },
                    )
                }

                is CardListState.Error -> {
                    ErrorFullScreen(
                        error = state.error,
                        onRetry = {
                            viewModel.emitIntent(CardListIntent.EnterScreen)
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
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .verticalScroll(rememberScrollState())
                .padding(
                    vertical = 16.dp,
                    horizontal = 24.dp,
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (cards.isNotEmpty()) {
                cards.forEach {
                    PaymentCard(cardUi = it)
                }

                DashedButton(
                    onClick = { onAddCard.invoke() },
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.add_a_card),
                    icon = painterResource(id = R.drawable.ic_plus)
                )
            } else {
                DashedButton(
                    onClick = { onAddCard.invoke() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
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
        modifier = Modifier.padding(
            vertical = 16.dp,
            horizontal = 24.dp,
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        (0..2).forEach { _ ->
            SkeletonShape(
                modifier = Modifier
                    .height(160.dp)
                    .fillMaxWidth(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ToolBar(onBack: () -> Unit) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.your_cards),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF262626),
                    textAlign = TextAlign.Center,
                ),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBack.invoke() },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_nav_back),
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
    )
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
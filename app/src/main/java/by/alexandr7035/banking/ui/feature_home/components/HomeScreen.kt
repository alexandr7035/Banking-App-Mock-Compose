package by.alexandr7035.banking.ui.feature_home.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.data.profile.Profile
import by.alexandr7035.banking.ui.components.DashedButton
import by.alexandr7035.banking.ui.components.ErrorFullScreen
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.components.header.ScreenHeader
import by.alexandr7035.banking.ui.core.NavEntries
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.extensions.showToast
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCard
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_home.AccountActionPanel
import by.alexandr7035.banking.ui.feature_home.AccountActionPanel_Skeleton
import by.alexandr7035.banking.ui.feature_home.model.HomeIntent
import by.alexandr7035.banking.ui.feature_home.model.HomeState
import by.alexandr7035.banking.ui.feature_home.model.HomeViewModel
import by.alexandr7035.banking.ui.feature_savings.components.SavingCard
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onGoToDestination: (navEntry: NavEntries) -> Unit = {}
) {

    LaunchedEffect(Unit) {
        viewModel.emitIntent(HomeIntent.EnterScreen)
    }

    val state = viewModel.state.collectAsStateWithLifecycle().value
    when (state) {
        is HomeState.Success -> HomeScreen_Ui(
            state = state,
            onGoToDestination = onGoToDestination
        )

        is HomeState.Loading -> HomeScreen_Skeleton()
        is HomeState.Error -> ErrorFullScreen(error = state.error, onRetry = {
            viewModel.emitIntent(HomeIntent.EnterScreen)
        })
    }
}

@Composable
fun HomeScreen_Ui(
    state: HomeState.Success,
    onGoToDestination: (navEntry: NavEntries) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(bottom = 24.dp)
    ) {
        val ctx = LocalContext.current

        ScreenHeader(
            toolbar = {
                HomeToolbar(
                    state.profile.name
                )
            }, panelVerticalOffset = 24.dp
        ) {
            AccountActionPanel(balance = 2000f, onActionClick = {
                // TODO
                ctx.showToast("TODO")
            })
        }

        Spacer(Modifier.height(8.dp))

        SectionTitle(stringResource(R.string.your_cards)) {
            onGoToDestination.invoke(NavEntries.CardList)
        }


        if (state.cards.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.cards.forEach {
                    PaymentCard(cardUi = it)
                }
            }
        } else {
            DashedButton(
                onClick = {
                    ctx.showToast("TODO")
                },
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .height(160.dp)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.add_a_card)
            )
        }

        if (state.savings.isNotEmpty()) {

            Spacer(Modifier.height(8.dp))

            SectionTitle(stringResource(R.string.your_saving)) {
                ctx.showToast("TODO")
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize()
                    .padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                state.savings.forEach {
                    SavingCard(savingUi = it)
                }
            }
        }
    }
}

@Composable
private fun SectionTitle(
    title: String, onViewMore: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title, style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF100D40),
            )
        )

        TextButton(
            onClick = { onViewMore?.invoke() }, enabled = onViewMore != null
        ) {
            Text(
                text = stringResource(R.string.view_all), style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF100D40),
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeToolbar(name: String) {
    val ctx = LocalContext.current

    TopAppBar(
        title = {
            Column(Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = stringResource(id = R.string.welcome_back), style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xB8FFFFFF),
                    )
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = name, style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFFFFFF),
                    ), maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
            IconButton(onClick = {
                ctx.showToast("TODO")
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp)
    )
}

@Composable
private fun HomeScreen_Skeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
            .padding(bottom = 24.dp)
    ) {
        ScreenHeader(
            toolbar = {}, panelVerticalOffset = 24.dp
        ) {
            AccountActionPanel_Skeleton()
        }

        Spacer(Modifier.height(8.dp))

        SectionTitle(stringResource(R.string.your_cards))

        SkeletonShape(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(160.dp)
        )

        Spacer(Modifier.height(8.dp))

        SectionTitle(stringResource(R.string.your_saving))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            List(3) {
                SkeletonShape(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreen_Preview() {
    ScreenPreview {
        HomeScreen_Ui(HomeState.Success(profile = Profile.mock(), cards = List(3) {
            CardUi.mock()
        }, savings = List(3) {
            SavingUi.mock()
        }))
    }
}


@Preview
@Composable
fun HomeScreen_Skeleton_Preview() {
    ScreenPreview {
        HomeScreen_Skeleton()
    }
}

@Preview
@Composable
fun HomeScreen_Empty() {
    ScreenPreview {
        HomeScreen_Ui(
            HomeState.Success(
                profile = Profile.mock(), cards = emptyList(), savings = emptyList()
            )
        )
    }
}
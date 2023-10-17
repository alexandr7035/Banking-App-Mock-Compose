package by.alexandr7035.banking.ui.feature_savings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Scaffold
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_savings.components.SavingCard
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun SavingsScreen(
    viewModel: SavingsViewModel = koinViewModel(),
    onBack: () -> Unit,
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    LaunchedEffect(Unit) {
        viewModel.emitIntent(SavingsListIntent.EnterScreen)
    }

    Scaffold(
        topBar = {
            SecondaryToolBar(onBack = onBack, title = UiText.StringResource(R.string.your_saving))
        }
    ) { pv ->
        Box(
            Modifier
                .fillMaxSize()
                .padding(
                    top = pv.calculateTopPadding(),
                    bottom = pv.calculateBottomPadding()
                )) {
            when (state) {
                is SavingsListState.Success -> {
                    SavingsScreen_Ui(savings = state.savings)
                }

                // TODO skeleton
                is SavingsListState.Loading -> FullscreenProgressBar()

                is SavingsListState.Error -> ErrorFullScreen(error = state.error)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SavingsScreen_Ui(
    savings: List<SavingUi>,
    onBack: () -> Unit = {},
) {

    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {

        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { 2 }
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                if (pagerState.currentPage < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[pagerState.currentPage]),
                        height = 4.dp,
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.background,
            divider = @Composable {
                Divider(
                    color = Color(0xFFC4C4C4),
                    thickness = 2.dp,
                    modifier = Modifier.offset(y = -1.dp)
                )
            },

            ) {
            PagerTab(
                isSelected = pagerState.currentPage == 0,
                text = "On Progress",
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )
            PagerTab(
                isSelected = pagerState.currentPage == 1,
                text = "Done",
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(1)
                    }
                }
            )
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> {
                    SavingsList(savings = savings.filter {
                        !it.isCompleted
                    })
                }

                1 -> {
                    SavingsList(savings = savings.filter {
                        it.isCompleted
                    })
                }
            }
        }

    }
}

@Composable
private fun SavingsList(savings: List<SavingUi>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp
            ),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(savings) { savingUi ->
            SavingCard(savingUi = savingUi)
        }
    }
}

@Composable
private fun PagerTab(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit = {}
) {
    Tab(
        selected = isSelected,
        onClick = onClick
    ) {

        Box(
            modifier = Modifier.padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {

            if (isSelected) {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF100D40),
                    )
                )
            } else {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFFCCCCCC),
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun SavingsScreen_Preview() {
    ScreenPreview {
        SavingsScreen_Ui(savings = List(5) { SavingUi.mock() })
    }
}
package by.alexandr7035.banking.ui.feature_savings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
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
                is SavingsListState.Loading -> SavingsScreen_Skeleton()

                is SavingsListState.Error -> ErrorFullScreen(error = state.error)
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SavingsScreen_Ui(
    savings: List<SavingUi>,
) {

    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        // TODO core code for pager and tablayout
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { 2 }
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            // TODO make indicator more close to design
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
                    color = Color(0xFFF2F2F2),
                    thickness = 2.dp,
                    modifier = Modifier.offset(y = (-1).dp)
                )
            },

            ) {
            PagerTab(
                isSelected = pagerState.currentPage == 0,
                text = stringResource(R.string.on_progress),
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(0)
                    }
                }
            )
            PagerTab(
                isSelected = pagerState.currentPage == 1,
                text = stringResource(R.string.done),
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
private fun SavingsScreen_Skeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp,
                end = 24.dp,
                top = 24.dp
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(4) {
            SkeletonShape(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp))
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
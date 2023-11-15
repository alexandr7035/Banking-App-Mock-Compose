package by.alexandr7035.banking.ui.feature_transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
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
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.components.error.ErrorFullScreen
import by.alexandr7035.banking.ui.components.error.ErrorListItem
import by.alexandr7035.banking.ui.components.pages.Page
import by.alexandr7035.banking.ui.components.pages.PagerTabRow
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_transactions.components.TransactionCard
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionHistoryScreen(
    viewModel: TransactionHistoryViewModel = koinViewModel()
) {
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = "History", style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF262626),
                        textAlign = TextAlign.Center,
                    )
                )
            }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
        )
    }) { pv ->

        Box(
            Modifier
                .fillMaxSize()
                .padding(
                    top = pv.calculateTopPadding(),
                    bottom = pv.calculateBottomPadding()
                )
        ) {
            val state = viewModel.state.collectAsStateWithLifecycle().value



            when {
//                state.isLoading -> TransactionHistoryScreen_Skeleton()
                else -> TransactionHistoryScreen_Ui(
                    state = state,
                    onIntent = {
                        viewModel.emitIntent(it)
                    }
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.emitIntent(TransactionHistoryIntent.InitLoad)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionHistoryScreen_Ui(
    state: TransactionHistoryState,
    onIntent: (TransactionHistoryIntent) -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize()) {
        val pages: List<Page<Int>> = listOf(
            Page(0, title = stringResource(id = R.string.all)),
            Page(1, title = stringResource(id = R.string.send)),
            Page(2, title = stringResource(id = R.string.request)),
            Page(3, title = stringResource(id = R.string.top_up)),
        )

        val pagerState = rememberPagerState() { pages.size }

        PagerTabRow(
            tabs = pages,
            pagerState = pagerState
        )

        val txPagingState = state.transactionsPagingState.collectAsLazyPagingItems()

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { horizontalPage ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
            ) {
                items(txPagingState.itemCount) { index ->
                    val tx = txPagingState[index]
                    tx?.let {
                        TransactionCard(transactionUi = tx)
                    }
                }

                txPagingState.apply {

                    val loadState = loadState

                    when {
                        // Skeleton
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                TransactionHistoryScreen_Skeleton()
                            }
                        }

                        loadState.refresh is LoadState.Error -> {
                            val pagerError = txPagingState.loadState.refresh as LoadState.Error
                            item {
                                ErrorFullScreen(
                                    error = ErrorType.fromThrowable(
                                        e = pagerError.error
                                    ).asUiTextError(),
                                    imageSize = 100.dp,
                                    enableScroll = false,
                                    onRetry = {
                                        retry()
                                    }
                                )
                            }
                        }

                        // Load next page
                        loadState.append is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    DotsProgressIndicator(
                                        circleSize = 16.dp,
                                        spaceBetween = 4.dp,
                                        travelDistance = 12.dp
                                    )
                                }
                            }
                        }

                        // Load next page error
                        loadState.append is LoadState.Error -> {
                            val pagerError = txPagingState.loadState.append as LoadState.Error

                            item {
                                ErrorListItem(
                                    error = ErrorType.fromThrowable(
                                        e = pagerError.error
                                    ).asUiTextError(),
                                    onRetry = {
                                        retry()
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                val filter = when (page) {
                    0 -> null
                    1 -> TransactionType.SEND
                    2 -> TransactionType.RECEIVE
                    3 -> TransactionType.TOP_UP
                    else -> error("Unexpected page index $page. Failed to choose filter")
                }

                onIntent(TransactionHistoryIntent.ChangeTransactionFilter(filter))
            }
        }
    }
}

@Composable
private fun TransactionHistoryScreen_Skeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(10) {
            SkeletonShape(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp)
            )
        }
    }
}

@Composable
@Preview
fun TransactionHistoryScreen_Preview() {
    ScreenPreview {
        TransactionHistoryScreen()
    }
}
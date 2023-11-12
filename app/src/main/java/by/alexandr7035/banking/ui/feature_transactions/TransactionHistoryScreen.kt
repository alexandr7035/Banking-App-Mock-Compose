package by.alexandr7035.banking.ui.feature_transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.components.pages.PagerTabRow
import by.alexandr7035.banking.ui.components.pages.Page
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
                state.isLoading -> TransactionHistoryScreen_Skeleton()
                else -> TransactionHistoryScreen_Ui(
                    state = state
                )
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.emitIntent(TransactionHistoryIntent.InitialLoad)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TransactionHistoryScreen_Ui(
    state: TransactionHistoryState,
) {
    Column(modifier = Modifier.fillMaxSize()) {
//        Spacer(Modifier.height(24.dp))

        val pages: List<Page<Int>> = listOf(
            Page(0, title = "All"),
            Page(1, title = "Send"),
            Page(2, title = "Request"),
        )

        val pagerState = rememberPagerState() { pages.size }

        PagerTabRow(
            tabs = pages,
            pagerState = pagerState
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { page ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp)
            ) {
                items(state.transactions) {
                    TransactionCard(transactionUi = it)
                }
            }
        }
    }
}

@Composable
private fun TransactionHistoryScreen_Skeleton() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 24.dp, end = 24.dp, top = 24.dp
            )
            .verticalScroll(rememberScrollState()), verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        repeat(4) {
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
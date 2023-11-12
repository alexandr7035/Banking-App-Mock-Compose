package by.alexandr7035.banking.ui.feature_transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.pages.PagerTabRow
import by.alexandr7035.banking.ui.components.pages.Page
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransactionHistoryScreen() {
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
        Column(modifier = Modifier.padding(pv)) {
            Spacer(Modifier.height(24.dp))

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
                state = pagerState, modifier = Modifier.fillMaxSize()
            ) { page ->
                // TODO
            }
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
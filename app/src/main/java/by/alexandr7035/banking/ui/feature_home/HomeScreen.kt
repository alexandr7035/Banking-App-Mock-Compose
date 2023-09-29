package by.alexandr7035.banking.ui.feature_home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.header.ScreenHeader
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun HomeScreen() {
    HomeScreen_Ui()
}

@Composable
fun HomeScreen_Ui() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(
                rememberScrollState()
            )
    ) {
        ScreenHeader(
            toolbar = {
                HomeToolbar(
                    // TODO
                    name = "Alexander Michael"
                )
            },
            panelVerticalOffset = 24.dp
        ) {
            AccountActionPanel(balance = 2000f, onActionClick = {
                // TODO
            })
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeToolbar(
    name: String,

    ) {
    TopAppBar(
        title = {
            Column(Modifier.padding(horizontal = 8.dp)) {
                Text(
                    text = stringResource(id = R.string.welcome_back),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xB8FFFFFF),
                    )
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = name,
                    style = TextStyle(
                        fontSize = 18.sp,
                        lineHeight = 26.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_bell),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp)
    )
}


@Preview
@Composable
fun HomeScreen_Preview() {
    ScreenPreview {
        HomeScreen_Ui()
    }
}
package by.alexandr7035.banking.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondaryToolBar(
    onBack: () -> Unit,
    title: UiText,
    actions: @Composable RowScope.() -> Unit = {},
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = Color(0xFF262626),
    modifier: Modifier = Modifier
) {

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title.asString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = contentColor,
                    textAlign = TextAlign.Center,
                ),
            )
        },
        navigationIcon = {
            IconButton(
                onClick = { onBack.invoke() }, modifier = Modifier.padding(start = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_nav_back),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(contentColor)
                )
            }
        },
        actions = actions,
        modifier = modifier.then(Modifier.fillMaxWidth()),
        colors = TopAppBarDefaults.topAppBarColors(containerColor = containerColor)
    )
}

@Preview
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
private fun SecondaryToolBar_Preview() {
    ScreenPreview {

        Scaffold(
            topBar = {
                SecondaryToolBar(
                    onBack = { },
                    title = UiText.DynamicString("Add a Card")
                )
            }
        ) { pv ->
            Box(
                Modifier
                    .padding(16.dp)
                    .background(Color.LightGray)
                    .fillMaxSize()) {
            }
        }
    }
}

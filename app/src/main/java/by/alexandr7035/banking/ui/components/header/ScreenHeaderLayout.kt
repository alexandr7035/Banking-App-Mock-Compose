package by.alexandr7035.banking.ui.components.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.SettingButton
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.theme.primaryFontFamily

// TODO fix according to design
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenHeader(
    content: @Composable BoxScope.() -> Unit
) {

    ConstraintLayout(Modifier.wrapContentSize()) {
        val (cover, panel, toolbar) = createRefs()

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .height(136.dp)
                .constrainAs(cover) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }, contentAlignment = Alignment.TopCenter
        ) {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Title", style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent),
                modifier = Modifier.wrapContentHeight().padding(top=16.dp)
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(), contentAlignment = Alignment.TopEnd
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cover_ellipse),
                    contentDescription = null,
                    modifier = Modifier.offset(x = maxHeight / 4, y = -maxHeight / 4)
                )
            }

        }

        Box(
            modifier = Modifier
                .wrapContentSize()
                .padding(horizontal = 24.dp)
                .constrainAs(panel) {
                    centerAround(cover.bottom)
                }, contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}


@Preview
@Composable
fun ScreenHeader_Preview() {
    ScreenPreview {
        Column() {
            ScreenHeader {
                SettingButton(
                    modifier = Modifier.wrapContentSize(), icon = painterResource(id = R.drawable.ic_lock_filled), text = "Test test test"
                ) {}
            }
        }
    }
}
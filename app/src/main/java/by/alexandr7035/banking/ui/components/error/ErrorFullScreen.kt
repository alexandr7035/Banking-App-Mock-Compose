package by.alexandr7035.banking.ui.components.error

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily


@Composable
fun ErrorFullScreen(
    error: UiText,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {

    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            modifier = modifier.then(
                Modifier
                    .height(maxHeight)
                    .width(maxWidth)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        vertical = 40.dp,
                        horizontal = 24.dp
                    )
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.weight(0.5f))

            Text(
                text = stringResource(R.string.error_happened),
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = error.asString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.img_error),
                contentDescription = "Error image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.weight(1f))

            if (onRetry != null) {
                PrimaryButton(
                    modifier = Modifier.fillMaxSize(),
                    text = stringResource(R.string.try_again),
                    onClick = onRetry
                )
            }
        }
    }
}


@Preview(widthDp = 360, heightDp = 720)
@Composable
fun ErrorFullScreen_Preview() {
    BankingAppTheme() {
        Surface() {
            ErrorFullScreen(
                error = UiText.DynamicString("Internet connection failed"),
                onRetry = {}
            )
        }
    }
}
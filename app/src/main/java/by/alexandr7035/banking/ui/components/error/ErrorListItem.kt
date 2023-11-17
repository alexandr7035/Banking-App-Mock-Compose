package by.alexandr7035.banking.ui.components.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryButton
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun ErrorListItem(
    error: UiText,
    modifier: Modifier = Modifier,
    onRetry: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier.weight(1f, fill = true)
        ) {
//            Text(
//                text = stringResource(id = R.string.error_happened),
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    lineHeight = 20.sp,
//                    fontFamily = primaryFontFamily,
//                    fontWeight = FontWeight.Bold,
//                    color = Color(0xFF333333),
//                ),
//            )

            Text(
                text = error.asString(),
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF333333),
                ),
            )
        }

        Spacer(Modifier.width(16.dp))

        SecondaryButton(
            onClick = onRetry ?: {},
            modifier = Modifier.wrapContentSize(),
            text = stringResource(id = R.string.try_again)
        )
    }
}

@Composable
@Preview
fun ErrorListItem_Preview() {
    ScreenPreview {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SkeletonShape(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            )
            SkeletonShape(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            )
            SkeletonShape(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp)
            )

            ErrorListItem(error = UiText.DynamicString("An unknown error"))
        }
    }
}

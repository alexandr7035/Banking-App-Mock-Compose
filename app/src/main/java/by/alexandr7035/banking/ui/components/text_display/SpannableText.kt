package by.alexandr7035.banking.ui.components.text_display

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.theme.Gray30
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun SpannableText(
    modifier: Modifier = Modifier,
    baseString: String,
    actionString: String,
    textAlign: TextAlign = TextAlign.Center,
    baseStyle: TextStyle = TextStyle(
        fontFamily = primaryFontFamily,
        fontSize = 12.sp,
        color = Gray30,
        textAlign = textAlign
    ),
    actionStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.SemiBold,
    ),
    divider: String = " ",
    omClick: () -> Unit = {}
) {

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val annotatedString = buildAnnotatedString {
            append("$baseString$divider")

            pushStringAnnotation(
                tag = actionString,
                annotation = actionString
            )

            withStyle(
                style = actionStyle,
            ) {
                append(actionString)
            }
        }

        ClickableText(
            text = annotatedString,
            style = baseStyle,
            onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let { span ->
                    omClick.invoke()
                }
            })
    }
}

@Preview
@Composable
fun SpannableText_Preview() {
    ScreenPreview {
        SpannableText(
            baseString = "Don't have an account?",
            actionString = "Sign Up"
        )
    }
}
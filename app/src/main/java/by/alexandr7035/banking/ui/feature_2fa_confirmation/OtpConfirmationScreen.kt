package by.alexandr7035.banking.ui.feature_2fa_confirmation

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.PrimaryTextField
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.text.SpannableText
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun OtpFactorConfirmationScreen(
    onIntent: (OtpConfirmationIntent) -> Unit = {},
    state: OtpConfirmationState
) {
    val focusManager = LocalFocusManager.current

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .padding(
                    vertical = 40.dp,
                    horizontal = 24.dp
                )
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.height(72.dp))

            Text(
                text = "Verify Account",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.height(16.dp))

            SpannableText(
                baseString = "Enter 4 digit code we have sent\n",
                baseStyle = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                ),
                actionString = "${state.codeSentTo}",
                actionStyle = SpanStyle(
                    fontSize = 14.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333),
                )
            )

            PrimaryTextField(
                value = state.code.value,
                onValueChange = {

                }
            )

            Text(
                text = "Haven’t received verification code?",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                )
            )

            TextButton(onClick = { /*TODO*/ }) {
                Text(
                    text = "Resend Code",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF100D40),
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline,
                    )
                )
            }

            Spacer(Modifier.weight(1f))

            PrimaryButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                text = "Verify Now"
            )
        }
    }
}

@Preview
@Composable
fun OtpConfirmationScreen_Preview() {
    ScreenPreview {
        OtpFactorConfirmationScreen(
            state = OtpConfirmationState(
                code = UiField("1111"),
                codeSentTo = "+6285788773880",
            )
        )
    }
}
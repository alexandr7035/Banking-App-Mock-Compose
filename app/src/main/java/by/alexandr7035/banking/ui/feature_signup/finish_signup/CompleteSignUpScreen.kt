package by.alexandr7035.banking.ui.feature_signup.finish_signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.text.SpannableText
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun CompleteSignUpScreen(
    onClose: () -> Unit = {},
    onGoToTermsAndConditions: () -> Unit = {}
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .verticalScroll(rememberScrollState())
                .padding(
                    vertical = 40.dp,
                    horizontal = 24.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(72.dp))

            Text(
                text = stringResource(R.string.account_created),
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
                text = stringResource(R.string.account_created_message),
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
                painter = painterResource(id = R.drawable.img_success),
                contentDescription = "Success image",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(200.dp)
            )

            Spacer(Modifier.weight(1f))

            PrimaryButton(
                modifier = Modifier.fillMaxSize(),
                text = stringResource(R.string.continue_msg),
                onClick = onClose
            )

            Spacer(Modifier.height(16.dp))

            SpannableText(
                baseString = "By clicking continue, you agree to our",
                baseStyle = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 22.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF100D40),
                    textAlign = TextAlign.Center,
                ),
                // TODO ToS link
                actionString = stringResource(R.string.terms_and_conditions),
                actionStyle = SpanStyle(
                    fontSize = 12.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    textDecoration = TextDecoration.Underline,
                ),
                divider = "\n"
            ) {
                onGoToTermsAndConditions()
            }
        }
    }
}

@Composable
@Preview
fun CompleteSignUp_Preview() {
    ScreenPreview {
        CompleteSignUpScreen()
    }
}
package by.alexandr7035.banking.ui.feature_login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DecoratedTextField
import by.alexandr7035.banking.ui.components.DecoratedPasswordTextField
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.extensions.showToast
import by.alexandr7035.banking.ui.theme.Gray30
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun LoginScreen() {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }) {
        Cover(Modifier.fillMaxWidth())

        Spacer(Modifier.height(32.dp))

        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        LoginForm(focusManager = focusManager, context = context)

        Box(Modifier.padding(horizontal = 24.dp)) {
            PrimaryButton(
                onClick = {
                    focusManager.clearFocus()
                    // TODO
                }, modifier = Modifier.fillMaxWidth(), text = stringResource(R.string.sign_in)
            )
        }

        Spacer(Modifier.height(16.dp))

        Row() {
            // TODO some generic code for spans
            val annotatedString = buildAnnotatedString {
                append("Don’t have an account ? ")

                val signUp = stringResource(R.string.sign_up)
                pushStringAnnotation(tag = signUp, annotation = signUp)

                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(signUp)
                }
            }

            ClickableText(text = annotatedString, style = TextStyle(
                fontFamily = primaryFontFamily,
                fontSize = 12.sp,
                color = Gray30,
            ), onClick = { offset ->
                annotatedString.getStringAnnotations(offset, offset).firstOrNull()?.let { span ->
                    context.showToast("TODO: Sign Up")
                }
            })
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}


@Composable
private fun Cover(
    modifier: Modifier
) {
    Column(
        modifier = modifier
            .then(Modifier.background(MaterialTheme.colorScheme.primary))
            .padding(top = 48.dp, bottom = 16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = "SHIELDPAY",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )

        // TODO vector images
        Row(
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(), verticalAlignment = Alignment.Bottom
        ) {

            Image(
                modifier = Modifier
                    .fillMaxHeight(0.7F)
                    .offset(x = -56.dp),
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0xFF585679))
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxHeight()
                    .offset(x = 32.dp)
                    .rotate(-45F),
                colorFilter = ColorFilter.tint(Color(0xFF585679))
            )
        }
    }
}

@Composable
private fun LoginForm(
    focusManager: FocusManager, context: Context
) {
    Column(
        modifier = Modifier.padding(
            horizontal = 24.dp, vertical = 36.dp
        )
    ) {
        Text(
            text = stringResource(R.string.email_address),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelSmall,
        )

        Spacer(modifier = Modifier.height(12.dp))


        val login = rememberSaveable {
            mutableStateOf("")
        }

        DecoratedTextField(
            modifier = Modifier.fillMaxWidth(), value = login.value, onValueChange = {
                login.value = it
            }, singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = stringResource(R.string.password),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.labelSmall,
        )

        Spacer(modifier = Modifier.height(12.dp))


        val password = rememberSaveable {
            mutableStateOf("")
        }

        DecoratedPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = password.value,
            onValueChange = {
                password.value = it
            },
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(
                onClick = {
                    focusManager.clearFocus()
                    // TODO
                    context.showToast("TODO: Forgot Password")
                }, colors = ButtonDefaults.textButtonColors(contentColor = Gray30)
            ) {
                Text(stringResource(R.string.forgot_password))
            }
        }


    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
fun LoginScreen_Preview() {
    ScreenPreview {
        LoginScreen()
    }
}
package by.alexandr7035.banking.ui.feature_login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.app_host.host_utils.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.forms.DecoratedFormField
import by.alexandr7035.banking.ui.components.forms.DecoratedPasswordFormField
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.components.text_display.SpannableText
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.core.extensions.showToast
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.Gray30
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginCompleted: () -> Unit = {},
    onGoToSignUp: () -> Unit
) {
    val context = LocalContext.current
    val state = viewModel.loginState.collectAsStateWithLifecycle().value
    val snackBarState = LocalScopedSnackbarState.current

    EventEffect(
        event = state.loginEvent,
        onConsumed = viewModel::onLoginEventConsumed
    ) { loginResult ->

        when (loginResult) {
            is OperationResult.Success -> onLoginCompleted.invoke()
            is OperationResult.Failure -> {
                val error = loginResult.error.errorType.asUiTextError().asString(context)
                snackBarState.show(error, SnackBarMode.Negative)
            }
        }
    }

    LoginScreen_Ui(
        state = state,
        onIntent = { viewModel.emitIntent(it) },
        onSignUp = onGoToSignUp
    )
}


@Composable
private fun LoginScreen_Ui(
    state: LoginScreenState,
    onIntent: (intent: LoginIntent) -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    BoxWithConstraints(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    focusManager.clearFocus()
                })
            }
    ) {

        Column(
            Modifier
                .width(maxWidth)
                .height(maxHeight)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        start = 24.dp,
                        end = 24.dp,
                        top = 40.dp,
                        bottom = 16.dp
                    )
                    .fillMaxWidth(),
                text = stringResource(id = R.string.app_name).uppercase(),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Cover(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {
                LoginForm(
                    state = state,
                    onIntent = onIntent,
                    onSignUp = onSignUp
                )
            }
        }

        if (state.isLoading) {
            FullscreenProgressBar()
        }
    }
}


@Composable
private fun LoginForm(
    state: LoginScreenState,
    onIntent: (intent: LoginIntent) -> Unit = {},
    onSignUp: () -> Unit = {}
) {
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(
                vertical = 36.dp,
                horizontal = 24.dp,
            )
    ) {
        Text(
            text = stringResource(R.string.welcome_back),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(36.dp))

        DecoratedFormField(
            fieldTitle = UiText.StringResource(R.string.email_address),
            onValueChange = {
                onIntent.invoke(
                    LoginIntent.LoginFieldChanged(
                        fieldType = LoginFieldType.EMAIL,
                        fieldValue = it
                    )
                )
            },
            uiField = state.formFields.loginField
        )

        Spacer(modifier = Modifier.height(24.dp))

        DecoratedPasswordFormField(
            fieldTitle = UiText.StringResource(R.string.password),
            onValueChange = {
                onIntent.invoke(
                    LoginIntent.LoginFieldChanged(
                        fieldType = LoginFieldType.PASSWORD,
                        fieldValue = it
                    )
                )
            },
            uiField = state.formFields.passwordField
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

        Spacer(Modifier.height(36.dp))

        Box(Modifier.padding(horizontal = 24.dp)) {
            PrimaryButton(
                onClick = {
                    focusManager.clearFocus()
                    onIntent.invoke(LoginIntent.SubmitForm)
                },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.sign_in)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        SpannableText(
            modifier = Modifier.fillMaxWidth(),
            baseString = stringResource(R.string.don_t_have_an_account),
            actionString = stringResource(R.string.sign_up)
        ) {
            onSignUp()
        }

        Spacer(modifier = Modifier.heightIn(min = 16.dp))
    }
}


@Composable
private fun Cover(
    modifier: Modifier
) {
    Row(
        modifier = modifier.then(
            Modifier
                .wrapContentHeight(
                    unbounded = true,
                    align = Alignment.Top
                )
                .background(MaterialTheme.colorScheme.primary)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        ),
        verticalAlignment = Alignment.Bottom
    ) {

        Image(
            modifier = Modifier
                .height(110.dp)
                .offset(x = (-56).dp),
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color(0xFF585679)),
        )

        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier
                .height(160.dp)
                .offset(x = 32.dp)
                .rotate(-45F),
            colorFilter = ColorFilter.tint(Color(0xFF585679))
        )
    }
}


@Preview(device = Devices.PIXEL_2)
@Composable
fun LoginScreen_Preview() {
    ScreenPreview {
        LoginScreen_Ui(
            LoginScreenState(),
        )
    }
}

@Preview
@Composable
fun LoginForm_Preview() {
    BankingAppTheme {
        Surface {
            LoginForm(
                state = LoginScreenState(),
            )
        }
    }
}

@Preview(device = Devices.DEFAULT)
@Composable
fun LoginScreen_Error_Preview() {
    ScreenPreview {
        LoginScreen_Ui(
            LoginScreenState(
                formFields = LoginFormFields(
                    loginField = UiField("ex@com.2", UiText.DynamicString("Wrong e-mail format")),
                    passwordField = UiField("1223", UiText.DynamicString("Wrong password format"))
                )
            ),
        )
    }
}

@Preview(device = Devices.PIXEL_2)
@Composable
fun LoginScreen_Progress_Preview() {
    ScreenPreview {
        LoginScreen_Ui(
            LoginScreenState(
                isLoading = true
            ),
        )
    }
}
package by.alexandr7035.banking.ui.feature_signup

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.app_host.host_utils.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.CustomCheckBox
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.forms.DecoratedFormField
import by.alexandr7035.banking.ui.components.forms.DecoratedPasswordFormField
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.components.text.SpannableText
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun InitSignUpScreen(
    viewModel: InitSignUpViewModel = koinViewModel(),
    onGoToSignIn: () -> Unit = {},
    onGoToConfirmSignUp: (email: String) -> Unit = {},
    onGoToTermsAndConditions: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val snackBarState = LocalScopedSnackbarState.current
    val context = LocalContext.current

    SignUpStartScreen_Ui(
        state = state,
        onGoToSignIn = onGoToSignIn,
        onGoToTermsAndConditions = onGoToTermsAndConditions,
        onIntent = { viewModel.emitIntent(it) },
    )

    EventEffect(
        event = state.initSignUpEvent,
        onConsumed = viewModel::consumeInitSignUpEvent,
    ) { res ->
        when (res) {
            is OperationResult.Success -> {
                onGoToConfirmSignUp(state.fields.email.value)
            }

            is OperationResult.Failure -> {
                snackBarState.show(
                    message = res.error.errorType.asUiTextError().asString(context),
                    snackBarMode = SnackBarMode.Negative
                )
            }
        }
    }
}

@Composable
fun SignUpStartScreen_Ui(
    state: InitSignUpState,
    onIntent: (InitSIgnUpIntent) -> Unit = {},
    onGoToSignIn: () -> Unit = {},
    onGoToTermsAndConditions: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current

    BoxWithConstraints(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(onTap = {
                focusManager.clearFocus()
            })
        }
    ) {
        Column(
            Modifier
                .width(maxWidth)
                .height(maxHeight)
                .padding(
                    vertical = 40.dp, horizontal = 24.dp
                )
                .verticalScroll(rememberScrollState())
        ) {

            Spacer(Modifier.weight(1f))

            Text(
                text = "Welcome !", style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                ), modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))
            DecoratedFormField(
                fieldTitle = UiText.StringResource(R.string.full_name),
                onValueChange = {
                    onIntent(
                        InitSIgnUpIntent.FieldChanged(
                            fieldType = InitSignUpFieldType.FULL_NAME,
                            fieldValue = it
                        )
                    )
                },
                uiField = state.fields.fullName
            )

            Spacer(modifier = Modifier.height(20.dp))
            DecoratedFormField(
                fieldTitle = UiText.StringResource(R.string.email_address),
                onValueChange = {
                    onIntent(
                        InitSIgnUpIntent.FieldChanged(
                            fieldType = InitSignUpFieldType.EMAIL,
                            fieldValue = it
                        )
                    )
                },
                uiField = state.fields.email
            )

            Spacer(modifier = Modifier.height(20.dp))
            DecoratedPasswordFormField(
                fieldTitle = UiText.StringResource(R.string.password),
                onValueChange = {
                    onIntent(
                        InitSIgnUpIntent.FieldChanged(
                            fieldType = InitSignUpFieldType.PASSWORD,
                            fieldValue = it
                        )
                    )
                },
                uiField = state.fields.password
            )

            Row(
                modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomCheckBox(
                    checked = state.agreedTerms,
                    onValueChange = {
                        onIntent(InitSIgnUpIntent.ToggleTermsAgreed(it))
                    }
                )

                SpannableText(
                    baseString = "I confirm I agree with",
                    actionString = "Terms and Condition",
                    baseStyle = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start
                    ),
                    actionStyle = SpanStyle(
                        fontSize = 12.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF333333),
                        textDecoration = TextDecoration.Underline,
                    )
                ) {
                    onGoToTermsAndConditions()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                onClick = {
                    onIntent(InitSIgnUpIntent.SignUpConfirm)
                },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.sign_up),
                isEnabled = state.signUpBtnEnabled
            )

            SpannableText(
                baseString = stringResource(R.string.already_have_an_account),
                actionString = stringResource(R.string.sign_in),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp
                    ),
            ) {
                onGoToSignIn()
            }
        }
    }

    if (state.isLoading) {
        FullscreenProgressBar()
    }
}

@Composable
@Preview
fun SignUpStartScreen_Preview() {
    ScreenPreview {
        SignUpStartScreen_Ui(
            state = InitSignUpState()
        )
    }
}

@Composable
@Preview
fun SignUpStartScreen_Loading_Preview() {
    ScreenPreview {
        SignUpStartScreen_Ui(
            state = InitSignUpState(
                isLoading = true
            )
        )
    }
}
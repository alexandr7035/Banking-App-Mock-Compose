package by.alexandr7035.banking.ui.feature_signup.confirm_signup

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import by.alexandr7035.banking.ui.components.ScreenPreview
import org.koin.androidx.compose.koinViewModel

@Composable
fun ConfirmSignUpScreen(
    vIewModel: ConfirmEmailSignUpVIewModel = koinViewModel()
) {
    ConfirmSignUpScreen_Ui()
}

@Composable
fun ConfirmSignUpScreen_Ui() {
    Text(text="TODO 2FA")
}

@Preview
@Composable
fun ConfirmSignUpScreen_Preview() {
    ScreenPreview {
        ConfirmSignUpScreen_Ui()
    }
}
package by.alexandr7035.banking.ui.feature_signup

sealed class SignUpStartIntent {
    object EnterScreen: SignUpStartIntent()
    data class ToggleTermsAgreed(val agreed: Boolean): SignUpStartIntent()
//    data class SignUpFieldChanged(): SignUpStartIntent()
}
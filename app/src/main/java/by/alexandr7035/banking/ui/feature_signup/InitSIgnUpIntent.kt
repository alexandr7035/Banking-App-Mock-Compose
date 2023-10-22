package by.alexandr7035.banking.ui.feature_signup

sealed class InitSIgnUpIntent {
    object EnterScreen: InitSIgnUpIntent()
    data class ToggleTermsAgreed(val agreed: Boolean): InitSIgnUpIntent()
//    data class SignUpFieldChanged(): SignUpStartIntent()
}
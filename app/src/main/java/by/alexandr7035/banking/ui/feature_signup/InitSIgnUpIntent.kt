package by.alexandr7035.banking.ui.feature_signup

import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField

sealed class InitSIgnUpIntent {
    object EnterScreen : InitSIgnUpIntent()
    data class ToggleTermsAgreed(val agreed: Boolean) : InitSIgnUpIntent()
    data class FieldChanged(
        val fieldType: InitSignUpFieldType,
        val fieldValue: String
    ) : InitSIgnUpIntent()

    object SignUpConfirm : InitSIgnUpIntent()
}
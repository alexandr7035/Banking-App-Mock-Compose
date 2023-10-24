package by.alexandr7035.banking.ui.feature_2fa_confirmation

import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField

sealed class OtpConfirmationIntent {
    data class SubmitCode(val code: OtpConfirmationCode): OtpConfirmationIntent()
    object ResendCode: OtpConfirmationIntent()
    data class CodeChanged(val code: UiField): OtpConfirmationIntent()
}

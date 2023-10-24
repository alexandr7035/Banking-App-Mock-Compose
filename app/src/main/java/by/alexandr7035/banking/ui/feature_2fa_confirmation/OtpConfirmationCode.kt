package by.alexandr7035.banking.ui.feature_2fa_confirmation

data class OtpConfirmationCode(
    val confirmationType: OtpConfirmationType,
    val code: String,
)

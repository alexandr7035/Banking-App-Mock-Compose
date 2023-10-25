package by.alexandr7035.banking.ui.feature_otp

import by.alexandr7035.banking.domain.features.otp.model.OtpOperationType
import by.alexandr7035.banking.domain.features.otp.model.OtpType

sealed class OtpConfirmationIntent {
   data class RequestInitialOtp(
       val otpType: OtpType,
       val otpOperationType: OtpOperationType,
       val otpDestination: String,
   ): OtpConfirmationIntent()

    object SubmitCode: OtpConfirmationIntent()
    object ResendCode: OtpConfirmationIntent()
    data class CodeChanged(val code: String): OtpConfirmationIntent()
}

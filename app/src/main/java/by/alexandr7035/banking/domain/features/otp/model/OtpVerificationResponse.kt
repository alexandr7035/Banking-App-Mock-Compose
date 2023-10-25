package by.alexandr7035.banking.domain.features.otp.model

data class OtpVerificationResponse(
    val isSuccess: Boolean,
    val remainingAttempts: Int?
)

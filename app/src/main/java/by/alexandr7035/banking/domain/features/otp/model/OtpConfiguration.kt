package by.alexandr7035.banking.domain.features.otp.model

data class OtpConfiguration(
    val operationType: OtpOperationType,
    val otpType: OtpType,
    val otpDestination: String
)

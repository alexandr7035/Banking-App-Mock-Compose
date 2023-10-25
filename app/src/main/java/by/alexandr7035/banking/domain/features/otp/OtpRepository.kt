package by.alexandr7035.banking.domain.features.otp

import by.alexandr7035.banking.domain.features.otp.model.OtpConfiguration
import by.alexandr7035.banking.domain.features.otp.model.OtpGenerationResponse
import by.alexandr7035.banking.domain.features.otp.model.OtpVerificationResponse

interface OtpRepository {
    suspend fun requestGenerateOtpCode(otpConfiguration: OtpConfiguration): OtpGenerationResponse

    suspend fun verifyOtpCode(
        otpConfiguration: OtpConfiguration,
        code: String,
    ): OtpVerificationResponse
}
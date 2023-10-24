package by.alexandr7035.banking.domain.features.otp

import by.alexandr7035.banking.domain.features.otp.model.OtpConfiguration
import by.alexandr7035.banking.domain.features.otp.model.OtpVerificationResponse

class VerifyOtpUseCase(
    private val otpRepository: OtpRepository
) {
    suspend fun execute(
        otpConfiguration: OtpConfiguration,
        code: String,
    ): OtpVerificationResponse {
        return otpRepository.verifyOtpCode(
            otpConfiguration,
            code
        )
    }
}
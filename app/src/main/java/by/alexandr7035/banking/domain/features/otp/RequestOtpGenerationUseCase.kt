package by.alexandr7035.banking.domain.features.otp

import by.alexandr7035.banking.domain.features.otp.model.OtpConfiguration
import by.alexandr7035.banking.domain.features.otp.model.OtpGenerationResponse

class RequestOtpGenerationUseCase(
    private val otpRepository: OtpRepository
) {
    suspend fun execute(otpConfiguration: OtpConfiguration): OtpGenerationResponse {
        return otpRepository.requestGenerateOtpCode(otpConfiguration)
    }
}
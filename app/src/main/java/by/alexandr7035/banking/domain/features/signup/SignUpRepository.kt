package by.alexandr7035.banking.domain.features.signup

import by.alexandr7035.banking.domain.features.otp.model.OtpConfiguration
import by.alexandr7035.banking.domain.features.otp.model.OtpVerificationResponse

interface SignUpRepository {
    suspend fun signUpWithEmail(payload: SignUpPayload)

    suspend fun confirmSignUpWithEmail(
        otpCode: String,
        otpConfiguration: OtpConfiguration
    ): OtpVerificationResponse
}
package by.alexandr7035.banking.data.signup

import by.alexandr7035.banking.data.app.PrefKeys
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.otp.OtpRepository
import by.alexandr7035.banking.domain.features.otp.model.OtpConfiguration
import by.alexandr7035.banking.domain.features.otp.model.OtpVerificationResponse
import by.alexandr7035.banking.domain.features.signup.SignUpPayload
import by.alexandr7035.banking.domain.features.signup.SignUpRepository
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SignUpRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val otpRepository: OtpRepository,
    private val prefs: KsPrefs
) : SignUpRepository {
    override suspend fun signUpWithEmail(payload: SignUpPayload) = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        if (payload.email == MOCK_LOGIN_EMAIL && payload.password == MOCK_PASSWORD) {
            return@withContext
        } else {
            throw AppError(ErrorType.UNKNOWN_ERROR)
        }
    }

    override suspend fun confirmSignUpWithEmail(
        otpCode: String,
        otpConfiguration: OtpConfiguration
    ): OtpVerificationResponse = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        val signUpResult = OperationResult.runWrapped {
            otpRepository.verifyOtpCode(
                otpConfiguration = otpConfiguration,
                code = otpCode
            )
        }

        when (signUpResult) {
            is OperationResult.Success -> {
                // Successful signup
                if (signUpResult.data.isSuccess) {
                    prefs.push(PrefKeys.IS_LOGGED_IN.name, true)
                }

                return@withContext signUpResult.data
            }

            is OperationResult.Failure -> {
                throw signUpResult.error
            }
        }
    }

    companion object {
        private const val MOCK_LOGIN_EMAIL = "example@mail.com"
        private const val MOCK_PASSWORD = "1234567Ab"
        private const val MOCK_DELAY = 1000L
    }
}
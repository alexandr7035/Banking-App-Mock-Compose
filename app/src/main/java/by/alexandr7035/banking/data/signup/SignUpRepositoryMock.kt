package by.alexandr7035.banking.data.signup

import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.signup.SignUpPayload
import by.alexandr7035.banking.domain.features.signup.SignUpRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SignUpRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher
): SignUpRepository {
    override suspend fun signUpWithEmail(payload: SignUpPayload) = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        if (payload.email == MOCK_LOGIN_EMAIL && payload.password == MOCK_PASSWORD) {
            return@withContext
        }
        else {
            throw AppError(ErrorType.UNKNOWN_ERROR)
        }
    }

    // Here may be attempts counter
    override suspend fun confirmSignUp(confirmationCode: Int) = withContext(coroutineDispatcher){
        delay(MOCK_DELAY)

        if (confirmationCode == MOCK_CONFIRMATION_CODE) {
            return@withContext
        }
        else {
            throw AppError(ErrorType.WRONG_CONFIRMATION_CODE)
        }
    }

    companion object {
        private const val MOCK_LOGIN_EMAIL = "example@mail.com"
        private const val MOCK_PASSWORD = "1234567Ab"
        private const val MOCK_CONFIRMATION_CODE = 1111
        private const val MOCK_DELAY = 1000L
    }
}
package by.alexandr7035.banking.data.login

import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher
) : LoginRepository {

    override suspend fun loginWithEmail(email: String, password: String) = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        // TODO login attempts
        if (email == MOCK_LOGIN_EMAIL && password == MOCK_PASSWORD) {
            return@withContext
        }
        else if (email != MOCK_LOGIN_EMAIL) {
            throw AppError(ErrorType.USER_NOT_FOUND)
        }
        else {
            throw AppError(ErrorType.WRONG_PASSWORD)
        }
    }


    companion object {
        private const val MOCK_LOGIN_EMAIL = "example@mail.com"
        private const val MOCK_PASSWORD = "1234567Ab"
        private const val MOCK_DELAY = 1000L
    }
}
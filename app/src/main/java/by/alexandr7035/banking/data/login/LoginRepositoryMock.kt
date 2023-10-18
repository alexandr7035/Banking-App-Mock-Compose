package by.alexandr7035.banking.data.login

import by.alexandr7035.banking.data.app.PrefKeys
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val prefs: KsPrefs
) : LoginRepository {

    override suspend fun loginWithEmail(email: String, password: String) = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        // TODO login attempts
        if (email == MOCK_LOGIN_EMAIL && password == MOCK_PASSWORD) {
            prefs.push(PrefKeys.IS_LOGGED_IN.name, true)
            return@withContext
        }
        else if (email != MOCK_LOGIN_EMAIL) {
            throw AppError(ErrorType.USER_NOT_FOUND)
        }
        else {
            throw AppError(ErrorType.WRONG_PASSWORD)
        }
    }

    override fun checkIfLoggedIn(): Boolean {
        return prefs.pull(PrefKeys.IS_LOGGED_IN.name, false)
    }

    override fun logOut() {
        prefs.push(PrefKeys.IS_LOGGED_IN.name, false)
    }

    companion object {
        private const val MOCK_LOGIN_EMAIL = "example@mail.com"
        private const val MOCK_PASSWORD = "1234567Ab"
        private const val MOCK_DELAY = 1000L
    }
}
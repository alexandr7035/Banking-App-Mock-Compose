package by.alexandr7035.banking.data.login

import kotlinx.coroutines.delay
import kotlin.random.Random

class LoginRepositoryImpl : LoginRepository {
    override suspend fun login(email: String, password: String): Result<Unit> {
        delay(2000)
        val loginResult = Random.nextBoolean()

        return if (loginResult) {
            Result.success(Unit)
        } else {
            Result.failure(Exception("Wrong e-mail or password"))
        }
    }
}
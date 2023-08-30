package by.alexandr7035.banking.data.login

interface LoginRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}
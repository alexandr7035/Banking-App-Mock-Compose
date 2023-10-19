package by.alexandr7035.banking.domain.usecases.login

interface LoginRepository {
    suspend fun loginWithEmail(email: String, password: String)
    suspend fun checkIfLoggedIn(): Boolean
    suspend fun logOut()
}
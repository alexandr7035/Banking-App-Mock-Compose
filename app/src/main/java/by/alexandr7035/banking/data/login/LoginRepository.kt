package by.alexandr7035.banking.data.login

interface LoginRepository {
    suspend fun loginWithEmail(email: String, password: String)
    suspend fun checkIfLoggedIn(): Boolean
    fun logOut()
}
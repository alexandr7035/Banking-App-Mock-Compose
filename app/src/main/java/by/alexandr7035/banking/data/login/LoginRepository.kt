package by.alexandr7035.banking.data.login

interface LoginRepository {
    suspend fun loginWithEmail(email: String, password: String)
    fun checkIfLoggedIn(): Boolean
    fun logOut()
}
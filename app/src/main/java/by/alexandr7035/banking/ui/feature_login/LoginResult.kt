package by.alexandr7035.banking.ui.feature_login

sealed class LoginResult {
    object Success: LoginResult()

    data class Error(
        val errorUi: String
    ): LoginResult()
}

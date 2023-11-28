package by.alexandr7035.banking.domain.features.app_lock.model

sealed class AuthenticationResult {
    object Success: AuthenticationResult()

    data class Failure(
        val remainingAttempts: Int?
    ): AuthenticationResult()
}

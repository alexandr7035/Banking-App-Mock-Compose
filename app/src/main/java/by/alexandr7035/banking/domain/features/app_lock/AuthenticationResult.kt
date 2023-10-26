package by.alexandr7035.banking.domain.features.app_lock

// TODO separate with biometrics
sealed class AuthenticationResult {
    object Success: AuthenticationResult()

    data class Failure(
        val remainingAttempts: Int?
    ): AuthenticationResult()
}

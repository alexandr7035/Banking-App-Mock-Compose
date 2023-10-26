package by.alexandr7035.banking.domain.features.app_lock

interface AppLockRepository {
    fun authenticateWithPin(pin: String): AuthenticationResult
}
package by.alexandr7035.banking.domain.features.app_lock

interface AppLockRepository {
    fun setupAppLock(pinCode: String)
    fun authenticateWithPin(pin: String): AuthenticationResult
    fun checkIfAppLocked(): Boolean
}
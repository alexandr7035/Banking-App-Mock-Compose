package by.alexandr7035.banking.domain.features.app_lock

class AuthenticateWithPinUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(pin: String): AuthenticationResult {
        return appLockRepository.authenticateWithPin(pin)
    }
}
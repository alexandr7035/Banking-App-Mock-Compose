package by.alexandr7035.banking.domain.features.app_lock

class CheckAppLockedWithBiometricsUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(): Boolean {
        return appLockRepository.checkIfAppLockedWithBiometrics()
    }
}
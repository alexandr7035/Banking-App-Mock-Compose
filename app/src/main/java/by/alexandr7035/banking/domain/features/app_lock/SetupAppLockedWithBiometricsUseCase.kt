package by.alexandr7035.banking.domain.features.app_lock

class SetupAppLockedWithBiometricsUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute() {
        return appLockRepository.setupLockWithBiometrics(isLocked = true)
    }
}
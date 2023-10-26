package by.alexandr7035.banking.domain.features.app_lock

class CheckAppLockUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(): Boolean {
        return appLockRepository.checkIfAppLocked()
    }
}
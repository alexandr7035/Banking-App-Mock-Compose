package by.alexandr7035.banking.domain.features.app_lock

import by.alexandr7035.banking.domain.features.app_lock.model.BiometricsAvailability

class CheckIfBiometricsAvailableUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(): BiometricsAvailability {
        return appLockRepository.checkBiometricsAvailable()
    }
}
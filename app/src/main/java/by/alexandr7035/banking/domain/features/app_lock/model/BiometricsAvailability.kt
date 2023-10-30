package by.alexandr7035.banking.domain.features.app_lock.model

sealed class BiometricsAvailability {
    object Checking: BiometricsAvailability()
    object Available: BiometricsAvailability()
    object NotAvailable: BiometricsAvailability()
    object NotEnabled: BiometricsAvailability()
}
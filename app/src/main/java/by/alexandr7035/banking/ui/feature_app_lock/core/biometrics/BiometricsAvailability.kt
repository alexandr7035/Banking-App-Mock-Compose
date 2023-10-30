package by.alexandr7035.banking.ui.feature_app_lock.core.biometrics

sealed class BiometricsAvailability {
    object Checking: BiometricsAvailability()
    object Available: BiometricsAvailability()
    object NotAvailable: BiometricsAvailability()
    object NotEnabled: BiometricsAvailability()
}
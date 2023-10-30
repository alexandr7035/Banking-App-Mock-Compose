package by.alexandr7035.banking.ui.feature_app_lock.core.biometrics

sealed class BiometricAuthResult {
    object Success: BiometricAuthResult()
    data class Failure(val error: String): BiometricAuthResult()
}
package by.alexandr7035.banking.ui.feature_app_lock.setup_applock.biometrics

import android.content.Context
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult

sealed class EnableBiometricsIntent {
    data class RefreshBiometricsAvailability(val context: Context): EnableBiometricsIntent()
    data class AuthenticationResult(val result: BiometricAuthResult) : EnableBiometricsIntent()
}

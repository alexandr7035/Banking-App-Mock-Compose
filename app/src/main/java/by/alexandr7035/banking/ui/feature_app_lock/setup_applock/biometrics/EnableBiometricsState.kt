package by.alexandr7035.banking.ui.feature_app_lock.setup_applock.biometrics

import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsAvailability
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsPrompt
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class EnableBiometricsState(
    val prompt: BiometricsPrompt,
    val biometricsAvailability: BiometricsAvailability,
    val authResultEvent: StateEventWithContent<BiometricAuthResult> = consumed()
)

package by.alexandr7035.banking.ui.feature_app_settings

import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.app_lock.model.BiometricsAvailability
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class AppSettingsState(
    val biometricPrompt: BiometricsPromptUi = BiometricsPromptUi(
        title = UiText.StringResource(R.string.unlock_app_biometrics),
        cancelBtnText = UiText.StringResource(R.string.cancel)
    ),
    val biometricsAvailability: BiometricsAvailability,
    val isAppLockedWithBiometrics: Boolean,
    val biometricsAuthEvent: StateEventWithContent<BiometricAuthResult> = consumed()
)

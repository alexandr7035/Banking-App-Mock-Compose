package by.alexandr7035.banking.ui.feature_app_lock.lock_screen

import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockUiState
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import by.alexandr7035.banking.ui.feature_logout.LogoutState
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class LockScreenState(
    val uiState: AppLockUiState = AppLockUiState(),
    val biometricsPromptState: BiometricsPromptUi = BiometricsPromptUi(
        title = UiText.StringResource(R.string.unlock_app_biometrics),
        cancelBtnText = UiText.StringResource(R.string.cancel)
    ),

    val showBiometricsPromptEvent: StateEvent = consumed,
    val unlockWithPinEvent: StateEvent = consumed,
    val unlockWithBiometricsResultEvent: StateEventWithContent<BiometricAuthResult> = consumed(),

    val logoutState: LogoutState = LogoutState(),
)

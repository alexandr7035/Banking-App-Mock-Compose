package by.alexandr7035.banking.ui.feature_app_lock.setup_applock

import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockUiState
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class SetupAppLockState(
    val initialPin: String = "",
    val confirmPin: String = "",
    val isConfirmationStage: Boolean = false,
    val uiState: AppLockUiState = AppLockUiState(),
    val appLockCreatedEvent: StateEvent = consumed
)

package by.alexandr7035.banking.ui.feature_app_lock.lock_screen

import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockUiState
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class LockScreenState(
    val uiState: AppLockUiState = AppLockUiState(),
    val unlockResultEvent: StateEvent = consumed
)

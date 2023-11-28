package by.alexandr7035.banking.ui.feature_app_lock.setup_applock.pin

import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockUiState
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class CreatePinState(
    val initialPin: String = "",
    val confirmPin: String = "",
    val isConfirmationStage: Boolean = false,
    val uiState: AppLockUiState = AppLockUiState(),
    val pinCreatedEvent: StateEventWithContent<PinCreatedResult> = consumed()
)

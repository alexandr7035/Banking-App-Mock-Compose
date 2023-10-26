package by.alexandr7035.banking.ui.feature_app_lock

import by.alexandr7035.banking.ui.core.resources.UiText
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class AppLockUiState(
    val pinState: PinState = PinState(),
    val isLoading: Boolean = false,
    val unlockResultEvent: StateEvent = consumed
) {
    data class PinState(
        val pinLength: Int = 4,
        val pinValue: String = "",
        val error: UiText? = null,
    )
}

package by.alexandr7035.banking.ui.feature_app_lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.ui.core.resources.UiText
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppLockViewModel : ViewModel() {
    private val _state = MutableStateFlow(AppLockUiState())
    val state = _state.asStateFlow()
    private val pinLength = 4

    fun emitAppLockIntent(intent: AppLockIntent) {
        when (intent) {
            is AppLockIntent.PinFieldChange -> reduce(intent)
        }
    }

    private fun reduce(intent: AppLockIntent.PinFieldChange) {
        _state.update {
            it.copy(
                pinState = it.pinState.copy(
                    pinValue = intent.pin,
                    // Clear error on edit
                    error = null
                )
            )
        }

        if (intent.pin.length == pinLength) {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            viewModelScope.launch {
                delay(1000)

                _state.update {
//                    it.copy(
//                        isLoading = false,
//                        pinState = it.pinState.copy(
//                            pinValue = "",
//                            error = UiText.DynamicString("Invalid PIN. 3 attempts left")
//                        )
//                    )

                    it.copy(
                        unlockResultEvent = triggered,
                        isLoading = false,
                    )
                }
            }
        }
    }

    fun consumeUnlockEvent() {
        _state.update {
            it.copy(
                unlockResultEvent = consumed
            )
        }
    }
}
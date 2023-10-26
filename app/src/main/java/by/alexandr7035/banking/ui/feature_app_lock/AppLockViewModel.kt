package by.alexandr7035.banking.ui.feature_app_lock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.features.app_lock.AuthenticateWithPinUseCase
import by.alexandr7035.banking.domain.features.app_lock.AuthenticationResult
import by.alexandr7035.banking.ui.core.resources.UiText
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppLockViewModel(
    private val authenticateWithPinUseCase: AuthenticateWithPinUseCase
) : ViewModel() {

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
                val pinRes = authenticateWithPinUseCase.execute(
                    pin = _state.value.pinState.pinValue
                )

                when (pinRes) {
                    is AuthenticationResult.Success -> {
                        reduceSuccess()
                    }

                    is AuthenticationResult.Failure -> {
                        reduceError(pinRes)
                    }
                }
            }
        }
    }

    private fun reduceSuccess() {
        _state.update {
            it.copy(
                unlockResultEvent = triggered,
                isLoading = false,
            )
        }
    }

    private fun reduceError(pinRes: AuthenticationResult.Failure) {
        // TODO string template
        val postfix = if (pinRes.remainingAttempts != null) {
            " ${pinRes.remainingAttempts} attempts left"
        } else {
            ""
        }

        _state.update {
            it.copy(
                isLoading = false,
                pinState = it.pinState.copy(
                    pinValue = "",
                    error = UiText.DynamicString("Invalid PIN.$postfix")
                )
            )
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
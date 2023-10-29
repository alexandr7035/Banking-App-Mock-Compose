package by.alexandr7035.banking.ui.feature_app_lock.setup_applock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.app_lock.SetupAppLockUseCase
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_app_lock.AppLockIntent
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockUiState
import by.alexandr7035.banking.ui.feature_app_lock.core.AppLockViewModel
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupAppLockViewModel(
    private val setupAppLockUseCase: SetupAppLockUseCase
) : ViewModel(), AppLockViewModel {
    override val pinLength: Int = 4

    private val _state = MutableStateFlow(SetupAppLockState())
    val state = _state.asStateFlow()

    init {
        _state.update {
            it.copy(
                uiState = it.uiState.copy(
                    prompt = UiText.StringResource(R.string.create_pin)
                )
            )
        }
    }

    override fun emitAppLockIntent(intent: AppLockIntent) {
        when (intent) {
            is AppLockIntent.PinFieldChange -> reduce(intent)
        }
    }

    private fun reduce(intent: AppLockIntent.PinFieldChange) {
        _state.update {
            it.copy(
                uiState = it.uiState.copy(
                    pinValue = intent.pin,
                    // Clear error on edit
                    error = null
                )
            )
        }

        if (intent.pin.length == pinLength) {
            _state.update {
                it.copy(uiState = it.uiState.copy(isLoading = true))
            }
            reducePinCompleted(intent.pin)
        }
    }


    private fun reducePinCompleted(pin: String) {
        val currentState = _state.value

        viewModelScope.launch {
            // A little delay for UI UX
            delay(100)

            if (!currentState.isConfirmationStage) {
                // Reduce confirmation when first pin entered
                _state.update {
                    it.copy(
                        initialPin = pin,
                        isConfirmationStage = true,
                        // Reset UI state
                        uiState = AppLockUiState(
                            prompt = UiText.StringResource(R.string.confirm_pin)
                        )
                    )
                }
            } else {
                val pinsMatch = currentState.initialPin == pin
                if (pinsMatch) {
                    // TODO save pin
                    val savePinRes = setupAppLockUseCase.execute(
                        pinCode = pin
                    )

                    _state.update {
                        it.copy(
                            appLockCreatedEvent = triggered
                        )
                    }
                } else {
                    _state.value = SetupAppLockState(
                        // Reset default state
                        uiState = AppLockUiState(
                            prompt = UiText.StringResource(R.string.create_pin),
                            error = UiText.StringResource(R.string.pins_don_t_match)
                        )
                    )
                }
            }
        }
    }
}
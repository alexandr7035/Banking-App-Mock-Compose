package by.alexandr7035.banking.ui.feature_app_lock.setup_applock.pin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.app_lock.CheckIfBiometricsAvailableUseCase
import by.alexandr7035.banking.domain.features.app_lock.SetupAppLockUseCase
import by.alexandr7035.banking.domain.features.app_lock.model.BiometricsAvailability
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockUiState
import by.alexandr7035.banking.ui.feature_app_lock.core.AppLockIntent
import by.alexandr7035.banking.ui.feature_app_lock.core.AppLockViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePinViewModel(
    private val setupAppLockUseCase: SetupAppLockUseCase,
    private val checkIfBiometricsAvailableUseCase: CheckIfBiometricsAvailableUseCase
) : ViewModel(), AppLockViewModel {
    override val pinLength: Int = 4

    private val _state = MutableStateFlow(CreatePinState())
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
            // Do nothing on this user flow
            is AppLockIntent.BiometricsBtnClicked -> {}
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

                    // Save pin
                    val savePinRes = setupAppLockUseCase.execute(
                        pinCode = pin
                    )

                    // Check biometrics available for next step
                    val biometricsRes = checkIfBiometricsAvailableUseCase.execute()
                    val shouldRequestBiometrics = biometricsRes != BiometricsAvailability.NotAvailable

                    _state.update {
                        it.copy(
                            pinCreatedEvent = triggered(
                                PinCreatedResult(shouldRequestBiometrics = shouldRequestBiometrics)
                            )
                        )
                    }
                } else {
                    _state.value = CreatePinState(
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

    fun consumePinCreatedEvent() {
        _state.update {
            it.copy(pinCreatedEvent = consumed())
        }
    }
}
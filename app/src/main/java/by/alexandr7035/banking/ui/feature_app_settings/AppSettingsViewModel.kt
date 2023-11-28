package by.alexandr7035.banking.ui.feature_app_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.features.app_lock.CheckAppLockedWithBiometricsUseCase
import by.alexandr7035.banking.domain.features.app_lock.CheckIfBiometricsAvailableUseCase
import by.alexandr7035.banking.domain.features.app_lock.SetupAppLockedWithBiometricsUseCase
import by.alexandr7035.banking.domain.features.app_lock.model.BiometricsAvailability
import by.alexandr7035.banking.ui.feature_app_lock.core.BiometricsViewModel
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsIntent
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppSettingsViewModel(
    private val checkIfBiometricsAvailableUseCase: CheckIfBiometricsAvailableUseCase,
    private val checkAppLockedWithBiometricsUseCase: CheckAppLockedWithBiometricsUseCase,
    private val lockAppLockedWithBiometricsUseCase: SetupAppLockedWithBiometricsUseCase
) : ViewModel(), BiometricsViewModel {

    private val _state = MutableStateFlow(
        AppSettingsState(
            biometricsAvailability = BiometricsAvailability.Checking,
            isAppLockedWithBiometrics = false
        )
    )

    val state = _state.asStateFlow()

    override fun emitBiometricsIntent(intent: BiometricsIntent) {
        when (intent) {
            is BiometricsIntent.ConsumeAuthResult -> {
                viewModelScope.launch {
                    if (intent.result is BiometricAuthResult.Success) {
                        val currentBiometricsEnabled = _state.value.isAppLockedWithBiometrics
                        lockAppLockedWithBiometricsUseCase.execute(!currentBiometricsEnabled)
                    }

                    _state.update {
                        it.copy(biometricsAuthEvent = triggered(intent.result))
                    }
                }
            }

            is BiometricsIntent.RefreshBiometricsAvailability -> {
                _state.update {
                    it.copy(biometricsAvailability = BiometricsAvailability.Checking)
                }

                viewModelScope.launch {
                    val biometricsAvailable = checkIfBiometricsAvailableUseCase.execute()
                    val appLocked = checkAppLockedWithBiometricsUseCase.execute()

                    _state.update {
                        it.copy(
                            biometricsAvailability = biometricsAvailable,
                            isAppLockedWithBiometrics = appLocked
                        )
                    }
                }
            }
        }
    }

    fun consumeBiometricAuthEvent() {
        _state.update {
            it.copy(biometricsAuthEvent = consumed())
        }
    }
}
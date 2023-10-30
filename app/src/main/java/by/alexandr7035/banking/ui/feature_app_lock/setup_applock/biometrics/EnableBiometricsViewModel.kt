package by.alexandr7035.banking.ui.feature_app_lock.setup_applock.biometrics

import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.app_lock.SetupAppLockedWithBiometricsUseCase
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsAvailability
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsHelper
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsPrompt
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EnableBiometricsViewModel(
    private val setupAppLockedWithBiometricsUseCase: SetupAppLockedWithBiometricsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        EnableBiometricsState(
            prompt = BiometricsPrompt(
                title = UiText.StringResource(R.string.setup_biometrics),
                cancelBtnText = UiText.StringResource(R.string.cancel)
            ),
            biometricsAvailability = BiometricsAvailability.Checking,
        )
    )

    val state = _state.asStateFlow()

    fun emitIntent(intent: EnableBiometricsIntent) {
        when (intent) {
            is EnableBiometricsIntent.RefreshBiometricsAvailability -> {
                val availability = when (BiometricsHelper.checkIfBiometricsAvailable(intent.context)) {
                    BiometricManager.BIOMETRIC_SUCCESS -> {
                        BiometricsAvailability.Available
                    }

                    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                        BiometricsAvailability.NotEnabled
                    }
                    // All cases where not available
                    else -> {
                        BiometricsAvailability.NotAvailable
                    }
                }

                _state.update {
                    it.copy(biometricsAvailability = availability)
                }
            }

            is EnableBiometricsIntent.AuthenticationResult -> {
                if (intent.result is BiometricAuthResult.Success) {
                    setupAppLockedWithBiometricsUseCase.execute()
                }

                _state.update {
                    it.copy(authResultEvent = triggered(intent.result))
                }
            }
        }
    }

    fun consumeAuthEvent() {
        _state.update {
            it.copy(authResultEvent = consumed())
        }
    }
}
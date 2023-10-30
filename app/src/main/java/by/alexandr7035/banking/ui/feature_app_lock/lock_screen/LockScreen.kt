package by.alexandr7035.banking.ui.feature_app_lock.lock_screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.ui.app_host.host_utils.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.core.effects.OnResumeEffect
import by.alexandr7035.banking.ui.core.extensions.findActivity
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockScreen_Ui
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsHelper
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsIntent
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun LockScreen(
    viewModel: LockScreenViewModel = koinViewModel(),
    onAppUnlock: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val snackBarState = LocalScopedSnackbarState.current

    AppLockScreen_Ui(
        state = state.uiState,
        onIntent = {
            viewModel.emitAppLockIntent(it)
        }
    )

    EventEffect(
        event = state.unlockWithPinEvent,
        onConsumed = viewModel::consumeUnlockWithPinEvent
    ) {
        onAppUnlock()
    }

    EventEffect(
        event = state.showBiometricsPromptEvent,
        onConsumed = viewModel::consumeShowBiometricsPromptEvent
    ) {
        val activity = context.findActivity()
        activity?.let {
            BiometricsHelper.showPrompt(
                activity = activity,
                prompt = state.biometricsPromptState,
                onError = {
                    viewModel.emitBiometricsIntent(BiometricsIntent.AuthenticationResult(
                        BiometricAuthResult.Failure(it)
                    ))
                },
                onSuccess = {
                    viewModel.emitBiometricsIntent(BiometricsIntent.AuthenticationResult(
                        BiometricAuthResult.Success
                    ))
                }
            )
        }
    }

    EventEffect(
        event = state.unlockWithBiometricsResultEvent,
        onConsumed = viewModel::consumeBiometricAuthEvent,
    ) { biometricsRes ->
        when (biometricsRes) {
            is BiometricAuthResult.Success -> {
                onAppUnlock()
            }

            is BiometricAuthResult.Failure -> {
                snackBarState.show(biometricsRes.error, SnackBarMode.Negative)
            }
        }
    }

    OnResumeEffect {
        viewModel.emitBiometricsIntent(
            BiometricsIntent.RefreshBiometricsAvailability
        )
    }
}
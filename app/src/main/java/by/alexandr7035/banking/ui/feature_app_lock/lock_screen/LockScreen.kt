package by.alexandr7035.banking.ui.feature_app_lock.lock_screen

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockScreen_Ui
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun LockScreen(
    viewModel: LockScreenViewModel = koinViewModel(),
    onAppUnlock: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    AppLockScreen_Ui(
        state = state.uiState,
        onIntent = {
            viewModel.emitAppLockIntent(it)
        }
    )

    EventEffect(
        event = state.unlockResultEvent,
        onConsumed = viewModel::consumeUnlockEvent
    ) {
        onAppUnlock()
    }
}
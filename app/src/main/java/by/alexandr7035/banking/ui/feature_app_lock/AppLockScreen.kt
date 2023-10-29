package by.alexandr7035.banking.ui.feature_app_lock

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockScreen_Ui
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockUiState
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppLockScreen(
    viewModel: AppLockViewModel = koinViewModel(),
    onAppUnlock: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    AppLockScreen_Ui(
        // FIXME
//        state = state,
        state = AppLockUiState(),
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
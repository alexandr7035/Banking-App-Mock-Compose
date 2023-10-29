package by.alexandr7035.banking.ui.feature_app_lock.setup_applock

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.ui.feature_app_lock.components.AppLockScreen_Ui
import org.koin.androidx.compose.koinViewModel

@Composable
fun SetupAppLockScreen(
    viewModel: SetupAppLockViewModel = koinViewModel(),
    onAppLockSetup: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value

    AppLockScreen_Ui(
        state = state.uiState,
        onIntent = { viewModel.emitAppLockIntent(it) }
    )
}
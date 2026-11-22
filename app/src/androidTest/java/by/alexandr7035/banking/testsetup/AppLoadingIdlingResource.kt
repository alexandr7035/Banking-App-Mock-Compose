package by.alexandr7035.banking.testsetup

import androidx.compose.ui.test.IdlingResource
import by.alexandr7035.banking.ui.app_host.AppState
import by.alexandr7035.banking.ui.app_host.AppViewModel

class AppLoadingIdlingResource(
    private val viewModel: AppViewModel
) : IdlingResource {
    override val isIdleNow: Boolean
        get() = viewModel.appState.value !is AppState.Loading
}
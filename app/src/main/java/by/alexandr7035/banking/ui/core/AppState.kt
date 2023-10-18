package by.alexandr7035.banking.ui.core

import by.alexandr7035.banking.ui.core.resources.UiText

// Global app state, can include auth check result, app lock flag and so on
// consider researching better approach
sealed class AppState {
    object Loading: AppState()

    data class Ready(
        val isLoggedIn: Boolean = false,
        val passedOnboarding: Boolean = false,
    ): AppState()

    data class InitFailure(
        val error: UiText
    ): AppState()
}

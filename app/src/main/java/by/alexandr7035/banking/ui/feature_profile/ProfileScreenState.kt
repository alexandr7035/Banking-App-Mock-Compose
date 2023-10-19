package by.alexandr7035.banking.ui.feature_profile

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.core.resources.UiText
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class ProfileScreenState(
    val profile: ProfileUi? = null,
    val isProfileLoading: Boolean = true,
    val error: UiText? = null,
    val logoutState: LogoutState = LogoutState()
) {
    data class LogoutState(
        val isLoading: Boolean = false,
        val showLogoutDialog: Boolean = false,
        val logoutEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
    )
}


package by.alexandr7035.banking.ui.feature_profile

import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_logout.LogoutState
import by.alexandr7035.banking.ui.feature_profile.model.ProfileUi

data class ProfileScreenState(
    val profile: ProfileUi? = null,
    val isProfileLoading: Boolean = true,
    val error: UiText? = null,
    val logoutState: LogoutState = LogoutState(),
    val showMyQrDialog: Boolean = false,
    val showScanQrDialog: Boolean = false
)


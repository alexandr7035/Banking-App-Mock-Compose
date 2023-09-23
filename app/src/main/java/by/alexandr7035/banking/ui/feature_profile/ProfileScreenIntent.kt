package by.alexandr7035.banking.ui.feature_profile

sealed class ProfileScreenIntent {
    object LoadScreen: ProfileScreenIntent()
    object LogoutClick: ProfileScreenIntent()
    object ConsumeLogoutEvent: ProfileScreenIntent()
}

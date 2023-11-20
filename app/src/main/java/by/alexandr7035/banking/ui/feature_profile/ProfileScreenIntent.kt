package by.alexandr7035.banking.ui.feature_profile

sealed class ProfileScreenIntent {
    object EnterScreen: ProfileScreenIntent()
    data class ToggleMyQrDialog(val isShown: Boolean): ProfileScreenIntent()
}

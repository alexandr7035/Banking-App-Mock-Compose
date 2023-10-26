package by.alexandr7035.banking.ui.feature_app_lock

sealed class AppLockIntent {
    data class PinFieldChange(val pin: String): AppLockIntent()
}

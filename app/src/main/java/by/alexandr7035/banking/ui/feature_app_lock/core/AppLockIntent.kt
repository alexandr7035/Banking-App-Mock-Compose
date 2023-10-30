package by.alexandr7035.banking.ui.feature_app_lock.core

sealed class AppLockIntent {
    data class PinFieldChange(val pin: String): AppLockIntent()
    object BiometricsBtnClicked: AppLockIntent()
}

package by.alexandr7035.banking.ui.feature_profile

sealed class SettingEntry {
    object ScanQR: SettingEntry()
    object MyQR: SettingEntry()
    object ChangeProfile: SettingEntry()
    object ChangeEmail: SettingEntry()
    object ChangePassword: SettingEntry()
    object AccountSecurity: SettingEntry()
    object Help: SettingEntry()
}

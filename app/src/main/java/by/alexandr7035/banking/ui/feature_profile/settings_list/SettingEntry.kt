package by.alexandr7035.banking.ui.feature_profile.settings_list

import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.core.resources.UiText

sealed class SettingEntry(val uiTitle: UiText) {
    object ScanQR: SettingEntry(UiText.StringResource(R.string.scan_qr))
    object MyQR: SettingEntry(UiText.StringResource(R.string.my_qr))
    object ChangeProfile: SettingEntry(UiText.StringResource(R.string.change_personal_profile))
    object ChangeEmail: SettingEntry(UiText.StringResource(R.string.change_email_address))
    object ChangePassword: SettingEntry(UiText.StringResource(R.string.change_password))
    object AccountSecurity: SettingEntry(UiText.StringResource(R.string.account_security))
    object Help: SettingEntry(UiText.StringResource(R.string.help_and_privacy))
}

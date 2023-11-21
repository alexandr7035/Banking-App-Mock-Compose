package by.alexandr7035.banking.ui.feature_profile.menu

import androidx.annotation.DrawableRes
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.core.resources.UiText

sealed class MenuEntry(
    val uiTitle: UiText,
    @DrawableRes val iconRes: Int,
) {
    //    object ScanQR : MenuEntry(UiText.StringResource(R.string.scan_qr))
//    object MyQR : MenuEntry(UiText.StringResource(R.string.my_qr))
//    object ChangeProfile : MenuEntry(UiText.StringResource(R.string.change_personal_profile))
//    object ChangeEmail : MenuEntry(UiText.StringResource(R.string.change_email_address))
//    object ChangePassword : MenuEntry(UiText.StringResource(R.string.change_password))
//    object AccountSecurity : MenuEntry(UiText.StringResource(R.string.account_security))
    object Help : MenuEntry(UiText.StringResource(R.string.help_and_privacy), R.drawable.ic_help)
    object AppSettings : MenuEntry(UiText.StringResource(R.string.app_settings), R.drawable.ic_settings)
}

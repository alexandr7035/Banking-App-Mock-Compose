package by.alexandr7035.banking.ui.feature_profile.settings_list

import by.alexandr7035.banking.ui.core.resources.UiText

sealed class SettingListItem {
    data class Setting(
        val entry: SettingEntry,
        val onClick: (SettingEntry) -> Unit = {}
    ): SettingListItem()

    data class Section(
        val title: UiText
    ): SettingListItem()
}
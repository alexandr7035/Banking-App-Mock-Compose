package by.alexandr7035.banking.ui.feature_profile.settings_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.SettingButton
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun SettingList(
    modifier: Modifier,
    items: List<SettingListItem>,
    onSettingEntryClick: (SettingEntry) -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items.forEach {
            when (it) {
                is SettingListItem.Section -> {
                    Text(
                        text = it.title.asString(),
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF333333),
                        ), modifier = Modifier.padding(top = 8.dp)
                    )
                }

                is SettingListItem.Setting -> {
                    SettingButton(
                        modifier = Modifier.fillMaxWidth(),
                        icon = painterResource(id = R.drawable.ic_profile_filled),
                        text = it.entry.uiTitle.asString()
                    ) {
                        onSettingEntryClick(it.entry)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun SettingList_Preview() {
    BankingAppTheme() {
        Surface(color = MaterialTheme.colorScheme.background) {
            SettingList(
                modifier = Modifier.fillMaxSize(),
                items = listOf(
                    SettingListItem.Section(UiText.DynamicString("Account")),
                    SettingListItem.Setting(entry = SettingEntry.AccountSecurity),
                    SettingListItem.Setting(entry = SettingEntry.ChangePassword),
                    SettingListItem.Section(UiText.DynamicString("Account")),
                    SettingListItem.Setting(entry = SettingEntry.ChangePassword),
                )
            )
        }
    }
}
package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun ScreenSectionDivider(
    modifier: Modifier = Modifier,
    title: UiText,
    actionLabel: UiText? = UiText.StringResource(R.string.view_all),
    actionEnabled: Boolean = true,
    onAction: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .then(if (actionLabel == null) Modifier.padding(vertical = 12.dp) else Modifier)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title.asString(), style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF100D40),
            )
        )

        if (actionLabel != null) {
            TextButton(
                onClick = { onAction.invoke() },
                enabled = actionEnabled
            ) {
                Text(
                    text = actionLabel.asString(), style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF100D40),
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ScreenSectionDivider_Preview() {
    BankingAppTheme() {
        Column(
            modifier = Modifier
                .background(Color.LightGray)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ScreenSectionDivider(
                modifier = Modifier.fillMaxWidth(),
                title = UiText.DynamicString("Your Cards")
            )

            ScreenSectionDivider(
                modifier = Modifier.fillMaxWidth(),
                title = UiText.DynamicString("Your Cards"),
                actionLabel = null
            )
        }
    }
}
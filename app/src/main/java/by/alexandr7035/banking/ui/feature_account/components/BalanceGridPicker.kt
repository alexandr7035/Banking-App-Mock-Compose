package by.alexandr7035.banking.ui.feature_account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.domain.features.account.model.BalanceValue
import by.alexandr7035.banking.ui.components.SimpleGridView
import by.alexandr7035.banking.ui.feature_account.BalanceValueUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun BalanceGridPicker(
    proposedValues: Set<BalanceValue>,
    selectedValue: BalanceValue,
    modifier: Modifier = Modifier,
    onValueSelected: (BalanceValue) -> Unit = {}
) {
    val items = proposedValues.toList()

    SimpleGridView(
        modifier = modifier,
        columns = 3,
        countOfItems = items.size,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) { index ->
        val item = items[index]

        GridButton(
            value = item,
            isSelected = item == selectedValue,
            onClick = onValueSelected
        )
    }
}

@Composable
private fun GridButton(
    value: BalanceValue,
    isSelected: Boolean,
    onClick: (BalanceValue) -> Unit = {},
    background: Color = Color(0xFFFAFAFA),
    selectedColor: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = Color(0xFF999999),
    selectedTextColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val shape = RoundedCornerShape(6.dp)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = if (isSelected) selectedColor else background,
                shape = shape
            )
            .clip(shape)
            .clickable {
                onClick(value)
            }
            .padding(
                vertical = 14.dp,
                horizontal = 16.dp
            )
            .fillMaxWidth()
            .wrapContentSize()
    ) {
        Text(
            text = BalanceValueUi.mapFromDomain(value).balanceStr,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = if (isSelected) selectedTextColor else textColor,
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Composable
@Preview
fun BalanceGridPicker_Preview() {
    BankingAppTheme {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(36.dp)
        ) {
            BalanceGridPicker(
                selectedValue = BalanceValue.LongBalance(200),
                proposedValues = setOf(100, 200, 300, 400, 500, 600).map {
                    BalanceValue.LongBalance(it.toLong())
                }.toSet()
            )
        }
    }
}
package by.alexandr7035.banking.ui.feature_account.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalMinimumInteractiveComponentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.ui.components.CustomSlider
import by.alexandr7035.banking.ui.components.cards.PrimaryCard
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

// Make this view more flexible depending on currency (check steps)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceSliderPicker(
    modifier: Modifier = Modifier,
    selectedValue: MoneyAmount,
    onValueSelected: (MoneyAmount) -> Unit = {},
    btnStep: MoneyAmount = MoneyAmount(0.01F),
    sliderStep: MoneyAmount = MoneyAmount(1f),
    minValue: MoneyAmount? = MoneyAmount(0F),
    maxValue: MoneyAmount? = MoneyAmount(1000F),
    pickerEnabled: Boolean = true,
    error: UiText? = null
) {
    val min = minValue ?: MoneyAmount(0f)
    val max = maxValue ?: MoneyAmount(1000f)

    require(sliderStep > btnStep) { "Slider step must be bigger then btn step for UI consistency" }
    require(min < max) { "Slider max value must be greater then min value" }

    PrimaryCard(
        modifier = modifier,
        paddingValues = PaddingValues(vertical = 16.dp),
        corners = 16.dp
    ) {
        Column {
            Text(
                text = stringResource(R.string.set_the_nominal), style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF999999),
                ), modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                val decreaseEnabled = selectedValue - btnStep >= min
                IconButton(
                    onClick = {
                        onValueSelected(
                            selectedValue - btnStep
                        )
                    },
                    enabled = decreaseEnabled && pickerEnabled,
                ) {
                    Box(
                        Modifier.background(
                            shape = CircleShape, color = Color(0xFFC6C6C6)
                        )
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_minus_rounded),
                            contentDescription = "Minus",
//                        tint = if (decreaseEnabled) MaterialTheme.colorScheme.primary else  Color(0xFFF5F5F5)
                            tint = Color(0xFFF5F5F5),
                        )
                    }
                }

                Text(
                    text = MoneyAmountUi.mapFromDomain(selectedValue).amountStr, style = TextStyle(
                        fontSize = 32.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary,
                    )
                )

                val increaseEnabled = selectedValue + btnStep <= max
                IconButton(
                    onClick = {
                        onValueSelected(
                            selectedValue + btnStep
                        )
                    },
                    enabled = increaseEnabled && pickerEnabled,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus_rouned),
                        contentDescription = "Plus",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Prepare slider state
            val valueRange = (min.value..max.value)
            val stepsCountCheck = (max.value / sliderStep.value).toInt() - 1
            val stepsCount = if (stepsCountCheck >= 0) stepsCountCheck else 0

            CompositionLocalProvider(LocalMinimumInteractiveComponentSize provides Dp.Unspecified) {
                CustomSlider(
                    value = selectedValue.value,
                    valueRange = valueRange,
                    onValueChange = {
                        onValueSelected(MoneyAmount(it))
                    },
                    modifier = Modifier.padding(
                        horizontal = 6.dp
                    ),
                    stepsCount = stepsCount,
                    enabled = pickerEnabled
                )
            }

            if (error != null) {
                Text(
                    text = error.asString(),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.error
                    ),
                    modifier = Modifier.padding(start = 12.dp, top = 8.dp)
                )
            }
        }
    }
}


@Composable
@Preview
fun BalanceSliderPicker_Preview() {
    BankingAppTheme {
        Box(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            BalanceSliderPicker(
                selectedValue = MoneyAmount(100F),
                error = UiText.DynamicString("Insufficient balance")
            )
        }
    }
}
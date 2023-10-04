package by.alexandr7035.banking.ui.feature_cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.components.PrimaryTextField
import by.alexandr7035.banking.ui.feature_cards.helpers.CardNumberHelpers
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun CardNumberField(
    onPostValue: (value: String) -> Unit,
    modifier: Modifier = Modifier,
    type: KeyboardType = KeyboardType.Text,
) {

    val cardNumber = rememberSaveable {
        mutableStateOf("")
    }

    Column(modifier = modifier) {
        Text(
            text = "Card Number",
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF020614),
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        Spacer(Modifier.height(16.dp))

        PrimaryTextField(
            value = cardNumber.value,
            onValueChange = {
                cardNumber.value = it
                onPostValue.invoke(it)
            },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = type,
            ),
            visualTransformation = { number ->
                CardNumberHelpers.formatOtherCardNumbers(number)
            },
            trailingIcon = {
                Text("TODO")
            }
        )
    }
}


@Preview
@Composable
fun CardTextField_Preview() {
    BankingAppTheme() {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            CardNumberField(onPostValue = {})
        }
    }
}
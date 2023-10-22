package by.alexandr7035.banking.ui.components.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.components.DecoratedPasswordTextField
import by.alexandr7035.banking.ui.components.DecoratedTextField
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
private fun FormFieldWrapper(
    modifier: Modifier = Modifier,
    fieldTitle: UiText,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = fieldTitle.asString(),
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF808289),
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        content()
    }
}


@Composable
fun DecoratedFormField(
    modifier: Modifier = Modifier,
    fieldTitle: UiText,
    uiField: UiField = UiField(value = "", error = null),
    onValueChange: (String) -> Unit = {},
    singleLine: Boolean = true
) {

    FormFieldWrapper(
        modifier = modifier,
        fieldTitle = fieldTitle
    ) {
        DecoratedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiField.value,
            onValueChange = onValueChange,
            error = uiField.error?.asString(),
            singleLine = singleLine,
        )
    }
}

@Composable
fun DecoratedPasswordFormField(
    modifier: Modifier = Modifier,
    fieldTitle: UiText,
    uiField: UiField = UiField(value = "", error = null),
    onValueChange: (String) -> Unit = {},
) {

    FormFieldWrapper(
        modifier = modifier,
        fieldTitle = fieldTitle
    ) {
        DecoratedPasswordTextField(
            modifier = Modifier.fillMaxWidth(),
            value = uiField.value,
            onValueChange = onValueChange,
            error = uiField.error?.asString(),
        )
    }
}


@Preview
@Composable
fun Fields_Preview() {
    ScreenPreview {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            DecoratedFormField(
                modifier = Modifier.fillMaxWidth(),
                fieldTitle = UiText.DynamicString("Email Address")
            )

            DecoratedPasswordFormField(
                modifier = Modifier.fillMaxWidth(),
                fieldTitle = UiText.DynamicString("Password")
            )
        }
    }
}
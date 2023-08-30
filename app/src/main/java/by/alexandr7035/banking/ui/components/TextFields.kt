package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.theme.Gray25
import by.alexandr7035.banking.ui.theme.Gray5
import by.alexandr7035.banking.ui.theme.Gray60
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        color = Gray60,
        fontFamily = primaryFontFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    shape: Shape = RoundedCornerShape(4.dp),
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        interactionSource = interactionSource,
        enabled = enabled,
        singleLine = singleLine,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        readOnly = readOnly,
        maxLines = maxLines,
        textStyle = textStyle,
    ) { innerTextField ->
        TextFieldDefaults.OutlinedTextFieldDecorationBox(
            value = value,
            visualTransformation = visualTransformation,
            innerTextField = innerTextField,
            singleLine = singleLine,
            enabled = enabled,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(
                vertical = 14.dp, horizontal = 16.dp
            ),
            placeholder = placeholder,
            colors = colors,
            isError = error != null,
            leadingIcon = leadingIcon,
            trailingIcon = trailingIcon,
            supportingText = {
                if (error != null) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = error,
                        color = MaterialTheme.colorScheme.error
                    )
                } else supportingText?.invoke()
            },
            label = label,
            container = {
                TextFieldDefaults.OutlinedBorderContainerBox(enabled, error != null, interactionSource, colors, shape)
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DecoratedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        color = Gray60,
        fontFamily = primaryFontFamily,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp
    ),
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    error: String? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(4.dp),
) {

    val backgroundColor = remember {
        mutableStateOf(Gray5)
    }

    PrimaryTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.then(
            Modifier.onFocusChanged {
                if (it.isFocused) {
                    backgroundColor.value = Color.White
                } else {
                    backgroundColor.value = Gray5
                }
            }
        ),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        label = label,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        supportingText = supportingText,
        error = error,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        interactionSource = interactionSource,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = backgroundColor.value,
            unfocusedBorderColor = Gray5,
            focusedBorderColor = Gray25
        ),
        shape = shape
    )
}


@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {

    var passwordVisible = rememberSaveable { mutableStateOf(true) }

    PrimaryTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible.value) {
                painterResource(id = R.drawable.ic_visible)
            } else {
                painterResource(id = R.drawable.ic_invisible)
            }

            // Please provide localized description for accessibility services
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = icon, description)
            }
        }
    )

}


@Composable
fun DecoratedPasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    error: String? = null
) {

    var passwordVisible = rememberSaveable { mutableStateOf(true) }

    DecoratedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        error = error,
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val icon = if (passwordVisible.value) {
                painterResource(id = R.drawable.ic_visible)
            } else {
                painterResource(id = R.drawable.ic_invisible)
            }

            // Please provide localized description for accessibility services
            val description = if (passwordVisible.value) "Hide password" else "Show password"

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painter = icon, description)
            }
        }
    )

}

@Preview
@Composable
fun TextField_Preview() {
    Surface {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Text("Primary fields")

            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test test test",
                onValueChange = {}
            )

            PrimaryTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test test test",
                onValueChange = {},
                error = "Test error"
            )

            PasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test test test",
                onValueChange = {}
            )

            Text("Decorated fields")

            DecoratedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test test",
                onValueChange = {}
            )

            DecoratedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test test",
                onValueChange = {},
                error = "test error"
            )

            DecoratedPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                value = "Test test test",
                onValueChange = {}
            )
        }
    }
}
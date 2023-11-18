package by.alexandr7035.banking.ui.components.search

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChanged: (String) -> Unit = {},
    tint: UiText = UiText.StringResource(R.string.search_tint),
) {
    val shape = RoundedCornerShape(10.dp)
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .then(
                Modifier
                    .wrapContentHeight()
                    .clip(shape)
                    .border(
                        width = 1.dp,
                        color = Color(0xFF999999),
                        shape = shape
                    )
            )
    ) {
            val interactionSource = remember {
                MutableInteractionSource()
            }

            BasicTextField(
                value = query,
                onValueChange = onQueryChanged,
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                singleLine = true,
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF333333),
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                TextFieldDecorationBox(
                    value = query,
                    innerTextField = it,
                    placeholder = {
                        Text(
                            text = tint.asString(),
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight.Normal,
                                color = Color(0xFF999999),
                            )
                        )
                    },
                    interactionSource = interactionSource,
                    enabled = true,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        disabledTextColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = Color(0xFF999999)
                        )
                    },
                    trailingIcon = {
                        if (query.isNotBlank()) {
                            IconButton(
                                onClick = {
                                    onQueryChanged("")
                                    focusManager.clearFocus()
                                    keyboardController?.hide()
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_close_variant),
                                    contentDescription = stringResource(R.string.clear_search),
                                    modifier = Modifier.size(24.dp),
                                    tint = Color(0xFF999999)
                                )
                            }
                        }
                    },
                )
            }
    }
}

@Composable
@Preview
fun SearchField_Preview() {
    ScreenPreview {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            SearchField(Modifier.fillMaxWidth())
            SearchField(
                modifier = Modifier.fillMaxWidth(),
                query = "Test query"
            )
        }
    }
}
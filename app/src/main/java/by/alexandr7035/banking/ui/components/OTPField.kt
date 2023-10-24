package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun OTPField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit = {},
    otpLength: Int = 4,
    baseColor: Color = Color(0xFFC4C4C4),
    accentColor: Color = MaterialTheme.colorScheme.primary,
    underlineWidth: Dp = 2.dp,
) {
    val localDensity = LocalDensity.current
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(value = value, onValueChange = {
        if (it.length <= otpLength) {
            onValueChange(it)
        }

        if (it.length >= otpLength) {
            keyboardController?.hide()
            focusManager.clearFocus()
        }
    }, keyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Number,
    ), keyboardActions = KeyboardActions(onDone = {
        keyboardController?.hide()
        focusManager.clearFocus()
    }),
//        textStyle = TextStyle(
//            fontSize = 32.sp,
//            lineHeight = 32.sp,
//            fontFamily = primaryFontFamily,
//            fontWeight = FontWeight.Normal,
//            color = Color(0xFF100D40),
//            textAlign = TextAlign.Center,
//        ),
        singleLine = true, cursorBrush = SolidColor(accentColor), decorationBox = { innerTextField ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = modifier.then(Modifier.wrapContentHeight()),
            ) {
                repeat(otpLength) { index ->
                    val char = when {
                        index >= value.length -> ""
                        else -> value[index].toString()
                    }

                    val isCellHighlighted = value.length == index


//                    Box(
//                        modifier = Modifier.drawBehind {
//                            val strokeWidth = with(localDensity) {
//                                underlineWidth.toPx()
//                            }
//
//                            drawLine(
//                                color = if (isCellHighlighted) accentColor else baseColor,
//                                start = Offset(0f, size.height),
//                                end = Offset(size.width, size.height),
//                                strokeWidth = strokeWidth,
//                                cap = StrokeCap.Round
//                            )
//                        }
//                    ) {
//                        innerTextField()
//                    }


                    Text(text = char, style = TextStyle(
                        fontSize = 32.sp,
                        lineHeight = 32.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = if (isCellHighlighted) accentColor else baseColor,
                        textAlign = TextAlign.Center,
                    ), modifier = Modifier
                        .width(72.dp)
                        .drawBehind {
                            val strokeWidth = with(localDensity) {
                                underlineWidth.toPx()
                            }

                            drawLine(
                                color = if (isCellHighlighted) accentColor else baseColor,
                                start = Offset(0f, size.height),
                                end = Offset(size.width, size.height),
                                strokeWidth = strokeWidth,
                                cap = StrokeCap.Round
                            )
                        }
                            .padding(vertical = 16.dp),
                    )
                }
            }
        })
}

@Composable
@Preview
fun OtpField_Preview() {
    ScreenPreview {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            OTPField(
                value = "123",
            )
        }
    }
}
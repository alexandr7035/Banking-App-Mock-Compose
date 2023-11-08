package by.alexandr7035.banking.ui.feature_app_lock.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_app_lock.core.AppLockIntent
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import by.alexandr7035.banking.ui.theme.ubuntuFontFamily

@Composable
fun AppLockScreen_Ui(
    state: AppLockUiState,
    onIntent: (AppLockIntent) -> Unit = {},
) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(maxHeight)
                .width(maxWidth)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(Modifier.weight(1f))

            Text(
                text = state.prompt.asString(), style = TextStyle(
                    fontSize = 24.sp,
                    lineHeight = 34.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(Modifier.weight(1f))
            Spacer(modifier = Modifier.height(24.dp))

            PinIndicator(
                itemCount = state.pinLength,
                filledCount = state.pinValue.length
            )

            if (state.error != null) {
                Text(
                    text = state.error.asString(),
                    style = TextStyle(
                        color = Color.Red,
                        fontFamily = primaryFontFamily,
                        fontSize = 14.sp,
                    ),
                    modifier = Modifier.padding(top = 20.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            if (state.showBiometricsBtn) {
                // Use textbutton instead of icon due to ripple
                TextButton(
                    onClick = {
                        onIntent(AppLockIntent.BiometricsBtnClicked)
                    },
                    modifier = Modifier.wrapContentSize(),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fingerprint),
                        contentDescription = stringResource(id = R.string.unlock_app_biometrics),
                        modifier = Modifier.size(72.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(Modifier.weight(2f))

            PinKeyboard(
                onIntent = onIntent,
                pinLength = state.pinLength,
                pinValue = state.pinValue
            )
        }

        if (state.isLoading) {
            FullscreenProgressBar()
        }
    }
}


@Composable
private fun PinKeyboard(
    pinLength: Int,
    pinValue: String,
    onIntent: (AppLockIntent) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {

        val btnModifier = Modifier.weight(1f)

        val onDigitClick: (Int) -> Unit = { clickedDigit ->
            if (pinValue.length < pinLength) {
                val updatedPin = pinValue + clickedDigit.toString()
                onIntent(AppLockIntent.PinFieldChange(updatedPin))
            }
        }

        val onEraseClick: () -> Unit = { ->
            if (pinValue.isNotEmpty()) {
                val updatedPin = pinValue.substring(0, pinValue.length - 1)
                onIntent(AppLockIntent.PinFieldChange(updatedPin))
            }
        }

        PinRow {
            PinTextButton(
                value = 1,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
            PinTextButton(
                value = 2,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
            PinTextButton(
                value = 3,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
        }

        PinRow {
            PinTextButton(
                value = 4,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
            PinTextButton(
                value = 5,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
            PinTextButton(
                value = 6,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
        }

        PinRow {
            PinTextButton(
                value = 7,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
            PinTextButton(
                value = 8,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
            PinTextButton(
                value = 9,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )
        }

        PinRow {
            Box(btnModifier)
            PinTextButton(
                value = 0,
                modifier = btnModifier,
                onValueClicked = onDigitClick
            )

            IconButton(
                onClick = onEraseClick,
                modifier = btnModifier.then(Modifier.padding(vertical = 4.dp))
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_erase),
                    contentDescription = "Erase button",
                )
            }
        }
    }
}

@Composable
private fun PinRow(
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentHeight(),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        content()
    }
}

@Composable
private fun PinTextButton(
    modifier: Modifier = Modifier,
    value: Int,
    onValueClicked: (Int) -> Unit = {},
    cornerRadius: Dp = 16.dp,
) {
    val interactionState = remember { MutableInteractionSource() }
    val shape = RoundedCornerShape(cornerRadius)

    Box(modifier = modifier.then(
        Modifier
            .clip(shape)
            .clickable(
                indication = rememberRipple(
                    color = Color.Gray,

                    ), interactionSource = interactionState
            ) {
                onValueClicked(value)
            }
            .padding(
                vertical = 16.dp,
//                horizontal = 54.dp
            ))) {
        Text(
            text = value.toString(), style = TextStyle(
                fontSize = 20.sp,
                fontFamily = ubuntuFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF121025),
                textAlign = TextAlign.Center,
            ), modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun PinIndicator(
    itemCount: Int,
    filledCount: Int,
    dotSize: Dp = 12.dp,
    dotSpacing: Dp = 34.dp,
    baseColor: Color = Color(0xFFC1C1C1),
    filledColor: Color = Color(0xFF333333)
) {

    Row(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight(), horizontalArrangement = Arrangement.spacedBy(dotSpacing)
    ) {
        repeat(itemCount) { index ->
            val isFilled = index < filledCount

            Box(
                modifier = Modifier
                    .size(dotSize)
                    .clip(CircleShape)
                    .background(
                        color = if (isFilled) filledColor else baseColor
                    )
            )
        }
    }
}

@Composable
@Preview
fun AppLockScreen_Preview() {
    ScreenPreview {
        AppLockScreen_Ui(
            AppLockUiState(
                prompt = UiText.DynamicString("Create PIN"),
                pinLength = 4,
                pinValue = "123"
            )
        )
    }
}

@Composable
@Preview
fun AppLockScreen_Error_Preview() {
    ScreenPreview {
        AppLockScreen_Ui(
            AppLockUiState(
                prompt = UiText.DynamicString("Unlock the app"),
                pinLength = 4,
                pinValue = "123",
                error = UiText.DynamicString("Wrong PIN. 3 attempts left")
            )
        )
    }
}

@Composable
@Preview
fun AppLockScreen_With_Biometrics_Preview() {
    ScreenPreview {
        AppLockScreen_Ui(
            AppLockUiState(
                prompt = UiText.DynamicString("Unlock the app"),
                pinLength = 4,
                pinValue = "123",
                showBiometricsBtn = true
            )
        )
    }
}
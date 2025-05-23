package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalMinimumInteractiveComponentEnforcement
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.theme.BankingAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    stepsCount: Int = 0,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    thumbRadiusDp: Dp = 24.dp
) {
    val interactionSource = remember { MutableInteractionSource() }

    CompositionLocalProvider(LocalMinimumInteractiveComponentEnforcement provides false) {
        Slider(
            value = value,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            valueRange = valueRange,
            enabled = enabled,
            modifier = modifier,
            thumb = {
                CustomSliderThumb(
                    interactionSource = interactionSource,
                    thumbRadiusDp = thumbRadiusDp,
                    enabled = enabled
                )
            },
            track = {
                SliderDefaults.Track(
                    sliderState = it,
                    colors = SliderDefaults.colors(
                        activeTrackColor = MaterialTheme.colorScheme.primary,
                        inactiveTrackColor = Color(0xFFE7E7E7),
                        activeTickColor = MaterialTheme.colorScheme.primary,
                        inactiveTickColor = Color(0xFFE7E7E7),
                    ),
                    enabled = enabled,
                    thumbTrackGapSize = 0.dp,
                    // hotfix to restore previous size of slider before material update
                    // used since TrackHeight is internal
                    modifier = Modifier.scale(scaleX = 1f, scaleY = 0.25f)
                )
            },
            steps = stepsCount,
        )
    }
}

@Composable
private fun CustomSliderThumb(
    interactionSource: MutableInteractionSource,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    thumbRadiusDp: Dp = 24.dp,
) {
    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> interactions.add(interaction)
                is PressInteraction.Release -> interactions.remove(interaction.press)
                is PressInteraction.Cancel -> interactions.remove(interaction.press)
                is DragInteraction.Start -> interactions.add(interaction)
                is DragInteraction.Stop -> interactions.remove(interaction.start)
                is DragInteraction.Cancel -> interactions.remove(interaction.start)
            }
        }
    }

    val elevation = if (interactions.isNotEmpty()) {
        1.dp
    } else {
        4.dp
    }

    val shape = CircleShape

    val modifierInternal = modifier
        .size(thumbRadiusDp)
        .indication(
            interactionSource = interactionSource,
            indication = ripple(
                bounded = false,
                radius = thumbRadiusDp / 2,
            )
        )
        .hoverable(interactionSource = interactionSource)
        .shadow(
            elevation = if (enabled) elevation else 0.dp,
            shape = shape,
            clip = false
        )
        .background(
            color = MaterialTheme.colorScheme.onPrimary,
            shape = shape
        )

    Icon(
        painter = painterResource(id = R.drawable.ic_slider_thumb),
        contentDescription = "Slider thumb",
        tint = if (enabled) MaterialTheme.colorScheme.primary else Color.LightGray,
        modifier = modifierInternal,
    )
}

@Composable
@Preview
fun CustomSlider_Preview() {
    BankingAppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomSlider(
                value = 0.5f,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                thumbRadiusDp = 48.dp
            )

            CustomSlider(
                value = 0.5f,
                onValueChange = {},
                modifier = Modifier.fillMaxWidth(),
                thumbRadiusDp = 48.dp,
                enabled = false
            )
        }
    }
}
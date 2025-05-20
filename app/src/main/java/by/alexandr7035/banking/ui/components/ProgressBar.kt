package by.alexandr7035.banking.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import kotlinx.coroutines.delay
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun FullscreenProgressBar(
    backgroundColor: Color = Color(0x80000000)
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .pointerInput(Unit) {
                // Skip touch events
            },
        contentAlignment = Alignment.Center
    ) {
        DotsProgressIndicator()
    }
}

@Composable
fun PercentageIndicator(
    modifier: Modifier = Modifier,
    percentage: Float,
    thickness: Dp = 5.dp,
    accentColor: Color = MaterialTheme.colorScheme.primary,
    emptyColor: Color = Color(0xFFF2F2F2)
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val percentageStr = floor(percentage * 100).roundToInt()

        Canvas(
            Modifier.fillMaxSize()
        ) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            val diameter = size.width - thickness.toPx()
            val radius = diameter / 2

            val rectSize = Size(diameter, diameter)
            val rectTopLeft = Offset(centerX - radius, centerY - radius)

            drawArc(
                color = emptyColor,
                startAngle = 0F,
                sweepAngle = 360F,
                useCenter = false,
                topLeft = rectTopLeft,
                size = rectSize,
                style = Stroke(thickness.toPx())
            )

            drawArc(
                color = accentColor,
                startAngle = 0F,
                sweepAngle = percentage * 360F,
                useCenter = false,
                topLeft = rectTopLeft,
                size = rectSize,
                style = Stroke(thickness.toPx())
            )
        }

        BasicText(
            text = "${percentageStr}%",
            style = TextStyle(
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF100D40),
                textAlign = TextAlign.Center,
            ),
            autoSize = TextAutoSize.StepBased(
                minFontSize = 4.sp,
                maxFontSize = 32.sp,
                stepSize = 2.sp,
            ),
            maxLines = 1,
            modifier = Modifier.padding(
                vertical = thickness + 4.dp,
                horizontal = thickness + 6.dp,
            ),
        )
    }
}

@Composable
fun DotsProgressIndicator(
    modifier: Modifier = Modifier,
    circleSize: Dp = 24.dp,
    spaceBetween: Dp = 12.dp,
    travelDistance: Dp = 20.dp,
    circleColor: Color = MaterialTheme.colorScheme.primary
) {
    val circles = listOf(
        remember { Animatable(0f) },
        remember { Animatable(0f) },
        remember { Animatable(0f) },
    )

    // Actual values during animation
    val circleValues = circles.map { it.value }
    val distance = with(LocalDensity.current) { travelDistance.toPx() }

    // Launch animation
    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(key1 = animatable) {
            delay(index * 100L)
            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = keyframes {
                        durationMillis = 1200
                        0.0f at 0 with LinearOutSlowInEasing
                        1.0f at 300 with LinearOutSlowInEasing
                        0.0f at 600 with LinearOutSlowInEasing
                        0.0f at 1200 with LinearOutSlowInEasing
                    },
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween)
    ) {
        circleValues.forEach { value ->
            Box(
                Modifier
                    .size(circleSize)
                    .graphicsLayer {
                        translationY = -value * distance
                    }
                    .background(
                        shape = CircleShape,
                        color = circleColor
                    )
            )
        }
    }


}

@Preview
@Composable
fun PercentageIndicator_Preview() {
    BankingAppTheme() {
        Surface(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            PercentageIndicator(percentage = 0.81F, modifier = Modifier.size(48.dp))
        }
    }
}

@Preview
@Composable
fun FullscreenProgressBar_Preview() {
    ScreenPreview {
        FullscreenProgressBar()
    }
}

@Preview
@Composable
fun DotsProgressIndicator_Preview() {
    BankingAppTheme() {
        Surface(
            Modifier
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
        ) {
            DotsProgressIndicator()
        }
    }
}
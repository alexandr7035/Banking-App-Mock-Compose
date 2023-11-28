package by.alexandr7035.banking.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun HorseshoeProgressIndicator(
    modifier: Modifier = Modifier,
    canvasSize: Dp = 300.dp,
    value: Float = 10f,
    maxValue: Float = 100f,
    backgroundIndicatorColor: Color = Color.LightGray,
    foregroundIndicatorColor: Color = MaterialTheme.colorScheme.primary,
    strokeWidth: Dp = 32.dp,
    innerComponent: @Composable ColumnScope.() -> Unit = {
        Text("Inner component")
        Text("56%")
    },
) {
    val density = LocalDensity.current

    var allowedIndicatorValue by remember {
        mutableStateOf(maxValue)
    }
    allowedIndicatorValue = if (value <= maxValue) {
        value
    } else {
        maxValue
    }

    var animatedIndicatorValue by remember { mutableStateOf(0f) }
    LaunchedEffect(key1 = allowedIndicatorValue) {
        animatedIndicatorValue = allowedIndicatorValue
    }

    val percentage = (animatedIndicatorValue / maxValue) * 100

    val sweepAngle by animateFloatAsState(
        targetValue = (2.4 * percentage).toFloat(), animationSpec = tween(1000)
    )

    Column(
        modifier = modifier.then(
            Modifier
                .size(canvasSize)
                .drawBehind {
                    val componentSize = size / 1.25f
                    backgroundIndicator(
                        componentSize = componentSize,
                        indicatorColor = backgroundIndicatorColor,
                        indicatorStrokeWidth = with(density) { strokeWidth.toPx() },
                    )
                    foregroundIndicator(
                        sweepAngle = sweepAngle,
                        componentSize = componentSize,
                        indicatorColor = foregroundIndicatorColor,
                        indicatorStrokeWidth = with(density) { strokeWidth.toPx() },
                    )
                }), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        innerComponent()
    }
}

fun DrawScope.backgroundIndicator(
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize, color = indicatorColor, startAngle = 150f, sweepAngle = 240f, useCenter = false, style = Stroke(
            width = indicatorStrokeWidth, cap = StrokeCap.Round
        ), topLeft = Offset(
            x = (size.width - componentSize.width) / 2f, y = (size.height - componentSize.height) / 2f
        )
    )
}

fun DrawScope.foregroundIndicator(
    sweepAngle: Float,
    componentSize: Size,
    indicatorColor: Color,
    indicatorStrokeWidth: Float,
) {
    drawArc(
        size = componentSize, color = indicatorColor, startAngle = 150f, sweepAngle = sweepAngle, useCenter = false, style = Stroke(
            width = indicatorStrokeWidth, cap = StrokeCap.Round
        ), topLeft = Offset(
            x = (size.width - componentSize.width) / 2f, y = (size.height - componentSize.height) / 2f
        )
    )
}

@Composable
@Preview(showBackground = true)
fun CustomComponentPreview() {
    HorseshoeProgressIndicator(Modifier.size(200.dp))
}
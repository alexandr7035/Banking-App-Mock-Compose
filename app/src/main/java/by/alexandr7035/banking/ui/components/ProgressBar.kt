package by.alexandr7035.banking.ui.components

import AutoSizeText
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun FullscreenProgressBar(
    backgroundColor: Color? = null
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor ?: Color(0x80000000))
            .pointerInput(Unit) {
                // Skip touch events
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 6.dp,
            modifier = Modifier.then(Modifier.size(56.dp))
        )
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

        AutoSizeText(
            text = "${percentageStr}%",
            style = TextStyle(
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF100D40),
                textAlign = TextAlign.Center,
            ),
            textAlignment = Alignment.Center,
            maxLines = 1,
            modifier = Modifier.padding(
                vertical = thickness + 4.dp,
                horizontal = thickness + 6.dp,
            ),
        )
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
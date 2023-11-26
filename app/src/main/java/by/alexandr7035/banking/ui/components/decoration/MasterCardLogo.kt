package by.alexandr7035.banking.ui.components.decoration

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun MasterCardLogo(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {

        val radius = this.size.height / 2

        drawCircle(
            color = Color(0x66FFFFFF),
            center = Offset(x = radius, y = radius),
            radius = radius
        )

        drawCircle(
            color = Color(0x33FFFFFF),
            center = Offset(x = this.size.width - radius, y = radius),
            radius = radius
        )
    }
}

@Preview
@Composable
fun MasterCardLogo_Preview() {
    MasterCardLogo(modifier = Modifier.size(width = 40.dp, height = 24.dp))
}
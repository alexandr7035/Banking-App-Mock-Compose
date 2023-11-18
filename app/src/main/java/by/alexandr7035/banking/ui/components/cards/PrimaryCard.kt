package by.alexandr7035.banking.ui.components.cards

import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PrimaryCard(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
    corners: Dp = 10.dp,
    elevation: Dp = 20.dp,
    content: @Composable () -> Unit
) {
    val containerColor = Color.White
    val shape = RoundedCornerShape(corners)

    val clickableModifier = if (onClick != null) {
        Modifier.clickable {
            onClick()
        }
    } else {
        Modifier
    }

    Box(
        modifier = modifier.then(
            Modifier
                .requireCardElevation(
                    shape = shape,
                    ambientColor = Color.Gray,
                    spotColor = Color.Gray,
                    requiredSize = elevation
                )
                .background(
                    color = containerColor,
                    shape = shape
                )
                .then(clickableModifier)
                .padding(paddingValues)
        )
    ) {
        content()
    }
}

private fun Modifier.requireCardElevation(
    shape: Shape,
    ambientColor: Color,
    spotColor: Color,
    requiredSize: Dp,
    uncoloredFallbackElevation: Dp = 8.dp
): Modifier {
    // Colored shadows introduced
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
        this.then(
            Modifier.shadow(
                elevation = requiredSize,
                spotColor = spotColor,
                ambientColor = ambientColor,
                shape = shape,
            )
        )
    } else {
        this.then(
            Modifier.shadow(
                elevation = uncoloredFallbackElevation,
                shape = shape,
            )
        )
    }
}
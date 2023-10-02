package by.alexandr7035.banking.ui.components.decoration

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer

@Composable
fun SkeletonShape(
    modifier: Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(10.dp)
) {
    Box(modifier = modifier.then(
        Modifier
            .shimmer()
            .background(
                color = Color.LightGray,
                shape = shape
            )))
}

@Composable
@Preview
fun SkeletonShape_Preview() {
    Surface(color = Color.White) {
        SkeletonShape(modifier = Modifier.size(100.dp, 50.dp))
    }
}
package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.alexandr7035.banking.ui.core.ScreenPreview

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

@Preview
@Composable
fun FullscreenProgressBar_Ui() {
    ScreenPreview {
        FullscreenProgressBar()
    }
}
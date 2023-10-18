package by.alexandr7035.banking.ui.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.ScreenPreview

@Composable
fun AppLoadingScreen() {
    // TODO ui
    FullscreenProgressBar(backgroundColor = Color.Transparent)
}

@Composable
@Preview
fun AppLoadingScreen_Preview() {
    ScreenPreview {
        AppLoadingScreen()
    }
}
package by.alexandr7035.banking.ui.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.ScreenPreview

@Composable
fun AppLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            painter = painterResource(
                id = R.drawable.logo
            ),
            contentDescription = "Logo",
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .size(160.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        DotsProgressIndicator(
            circleSize = 18.dp,
            travelDistance = 20.dp
        )
    }
}

@Composable
@Preview
fun AppLoadingScreen_Preview() {
    ScreenPreview {
        AppLoadingScreen()
    }
}
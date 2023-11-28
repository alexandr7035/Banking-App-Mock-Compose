package by.alexandr7035.banking.ui.feature_cards.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.feature_cards.helpers.CardNetwork
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.ubuntuFontFamily

@Composable
fun CardNetworkLogo(
    cardNetwork: CardNetwork,
    logoHeight: Dp = 24.dp,
) {
    when (cardNetwork) {
        CardNetwork.VISA -> {
            Image(
                painter = painterResource(id = R.drawable.ic_visa),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color(0x4DFFFFFF)),
                modifier = Modifier.height(logoHeight)
            )
        }

        CardNetwork.MAESTRO -> {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Canvas(
                    modifier = Modifier.size(
                        width = logoHeight * 1.65f,
                        height = logoHeight
                    )
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

                Text(
                    text = "maestro",
                    style = TextStyle(
                        fontSize = 10.sp,
                        fontFamily = ubuntuFontFamily,
                        color = Color(0x66FFFFFF)
                    )
                )
            }
        }

        CardNetwork.MASTERCARD -> {
            Canvas(
                modifier = Modifier.size(
                    width = logoHeight * 1.65f,
                    height = logoHeight
                )
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

        CardNetwork.UNKNOWN -> {}
    }
}


@Composable
@Preview
fun CardNetworkLogo_Preview() {
    BankingAppTheme {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primary)
                .wrapContentSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CardNetworkLogo(cardNetwork = CardNetwork.MAESTRO)
            CardNetworkLogo(cardNetwork = CardNetwork.MASTERCARD)
            CardNetworkLogo(cardNetwork = CardNetwork.VISA)
            CardNetworkLogo(cardNetwork = CardNetwork.UNKNOWN)
        }
    }
}
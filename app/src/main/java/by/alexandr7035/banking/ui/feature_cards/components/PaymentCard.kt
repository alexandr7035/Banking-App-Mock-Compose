package by.alexandr7035.banking.ui.feature_cards.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.decoration.DecorationCircle
import by.alexandr7035.banking.ui.components.decoration.DecorationRectangle
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentCard(
    modifier: Modifier = Modifier,
    cardUi: CardUi,
    isSelected: Boolean = false,
    onCLick: (cardId: String) -> Unit = {}
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = cardUi.cardColor
        ),
        shape = RoundedCornerShape(10.dp),
        onClick = { onCLick.invoke(cardUi.cardNumber) }
    ) {

        val selectedBorder = if (isSelected) {
            Modifier.border(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0x1AFFFFFF),
                        Color(0x80FFFFFF),
                        Color(0x4DFFFFFF),
                    )
                ),
                width = 8.dp,
                shape = RoundedCornerShape(10.dp)
            )
        } else {
            Modifier
        }

        Box(
            modifier = modifier
                .then(selectedBorder)
                .then(
                    Modifier
                        .height(IntrinsicSize.Max)
                        .wrapContentWidth()
                )
        ) {

            if (cardUi.isPrimary) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_bookmark_filled),
                    contentDescription = "Default card",
                    tint = Color(0x99FFFFFF),
                )
            }

            DecorationRectangle(
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = (-75).dp, y = (-75).dp)
            )

            DecorationCircle(
                modifier = Modifier
                    .size(150.dp)
                    .offset(x = 50.dp, y = 75.dp)
                    .align(Alignment.BottomEnd)
            )

            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .height(IntrinsicSize.Max)
                    .wrapContentWidth()
            ) {

                Column() {
                    Text(
                        text = stringResource(R.string.payment_card), style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xCCFFFFFF),
                        )
                    )

                    Spacer(Modifier.height(30.dp))

                    Text(
                        text = cardUi.cardNumber, style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFAF9FF),
                        )
                    )

                    Spacer(Modifier.height(50.dp))

                    val balance = cardUi.balanceFlow
                        .collectAsStateWithLifecycle(initialValue = cardUi.recentBalance)
                        .value

                    Text(
                        text = balance, style = TextStyle(
                            fontSize = 20.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFAF9FF),
                        )
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.End
                ) {
                    // TODO there may be other logo
                    MasterCardLogo(modifier = Modifier.size(width = 40.dp, height = 24.dp))

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = cardUi.expiration, style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xCCFFFFFF),
                        )
                    )
                }
            }
        }


    }
}

@Composable
@Preview
fun PaymentCard_Preview() {
    BankingAppTheme() {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            PaymentCard(
                cardUi = CardUi.mock()
            )

            PaymentCard(
                cardUi = CardUi.mock(),
                isSelected = true
            )

            PaymentCard(
                cardUi = CardUi.mock(Color(0xFF000000)),
            )

            PaymentCard(
                cardUi = CardUi.mock(Color(0xFF000000)),
                isSelected = true
            )
        }
    }
}
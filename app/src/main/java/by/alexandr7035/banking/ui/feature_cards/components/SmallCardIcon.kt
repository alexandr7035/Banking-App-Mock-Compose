package by.alexandr7035.banking.ui.feature_cards.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.extensions.formatCardNumber
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun SmallCardIcon() {
    val cardUi = CardUi.mock()

    Card(
        backgroundColor = MaterialTheme.colorScheme.primary,
        shape = RoundedCornerShape(4.dp),
    ) {
        Box(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .wrapContentWidth()
        ) {
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .height(IntrinsicSize.Max)
                    .wrapContentWidth()
            ) {

                Column() {
                    Text(
                        text = stringResource(R.string.payment_card), style = TextStyle(
                            fontSize = 2.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight(400),
                            color = Color(0xCCFFFFFF),
                        )
                    )

                    Spacer(Modifier.height(4.dp))

                    Text(
                        text = cardUi.cardNumber.formatCardNumber(), style = TextStyle(
                            fontSize = 3.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFAF9FF),
                        )
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = cardUi.balance, style = TextStyle(
                            fontSize = 4.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight(600),
                            color = Color(0xFFFAF9FF),
                        )
                    )
                }

                Column(
                    modifier = Modifier.fillMaxHeight(), horizontalAlignment = Alignment.End
                ) {
                    MasterCardLogo(modifier = Modifier.size(width = 6.dp, height = 3.5.dp))

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = cardUi.expiration, style = TextStyle(
                            fontSize = 2.sp,
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
fun SmallCard_Preview() {
    BankingAppTheme() {
        SmallCardIcon()
    }
}
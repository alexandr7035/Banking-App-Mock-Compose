package by.alexandr7035.banking.ui.feature_cards.components

import androidx.compose.foundation.background
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.decoration.DecorationCircle
import by.alexandr7035.banking.ui.components.decoration.DecorationRectangle
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun SmallCardIcon() {
    val cardUi = CardUi.mock()

    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
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
                        text = cardUi.cardNumber, style = TextStyle(
                            fontSize = 3.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFFFAF9FF),
                        )
                    )

                    Spacer(Modifier.height(6.dp))

                    Text(
                        text = cardUi.recentBalance, style = TextStyle(
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
fun SmallCardIconCustomizable(
    modifier: Modifier = Modifier,
    cardUi: CardUi = CardUi.mock(),
    numberSize: TextUnit = 16.sp,
    balanceSize: TextUnit = 20.sp,
    labelSize: TextUnit = 14.sp,
    dateSize: TextUnit = 12.sp,
    decorationSize: Dp = 50.dp
) {
    Box(
        modifier = modifier.then(
            Modifier.background(
                color = cardUi.cardColor,
                shape = RoundedCornerShape(4.dp)
            )
        )
    ) {

        DecorationRectangle(
            modifier = Modifier
                .size(decorationSize)
                .offset(x = (-decorationSize / 2), y = (-decorationSize / 2)),
            strokeWidth = decorationSize / 8
        )

        DecorationCircle(
            modifier = Modifier
                .size(decorationSize)
                .offset(x = decorationSize / 3, y = decorationSize / 2)
                .align(Alignment.BottomEnd),
            strokeWidth =  decorationSize / 8
        )

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .padding(4.dp)
        ) {

            Column() {
                Text(
                    text = stringResource(R.string.payment_card), style = TextStyle(
                        fontSize = labelSize,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(400),
                        color = Color(0xCCFFFFFF),
                    )
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = cardUi.cardNumber,
                    style = TextStyle(
                        fontSize = numberSize,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFFAF9FF),
                    ),
                )

                Spacer(Modifier.weight(1f))

                Text(
                    text = cardUi.recentBalance,
                    style = TextStyle(
                        fontFamily = primaryFontFamily,
                        fontSize = balanceSize,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFAF9FF),
                    ),
                )
            }

            Spacer(Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.End
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = cardUi.expiration, style = TextStyle(
                        fontSize = dateSize,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xCCFFFFFF),
                    )
                )
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

@Composable
@Preview
fun SmallCard_Customizable_Preview() {
    BankingAppTheme() {
        SmallCardIconCustomizable(
            modifier = Modifier
                .height(60.dp),
            numberSize = 6.sp,
            labelSize = 4.sp,
            balanceSize = 7.sp,
            dateSize = 4.sp,
            decorationSize = 40.dp
        )
    }
}
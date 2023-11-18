package by.alexandr7035.banking.ui.feature_cards.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.cards.PrimaryCard
import by.alexandr7035.banking.ui.components.dashedBorder
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun PanelCardPicker(
    isLoading: Boolean,
    selectedCard: CardUi?,
    onCardPickerClick: () -> Unit = {},
    cardSize: DpSize = DpSize(width = 90.dp, height = 60.dp)
) {
    PrimaryCard(
        paddingValues = PaddingValues(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            when {
                isLoading -> {

                    SkeletonShape(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(cardSize)
                    )

                    SkeletonShape(
                        modifier = Modifier
                            .height(14.dp)
                            .width(56.dp)
                    )

                    Spacer(
                        Modifier
                            .weight(1f)
                            .height(56.dp)
                    )

                    SkeletonShape(
                        modifier = Modifier
                            .height(24.dp)
                            .width(100.dp)
                            .padding(end = 12.dp)
                    )
                }

                selectedCard != null -> {
                    SmallCardIconCustomizable(
                        cardUi = selectedCard,
                        modifier = Modifier
                            .size(cardSize),
                        numberSize = 6.sp,
                        labelSize = 4.sp,
                        balanceSize = 7.sp,
                        dateSize = 4.sp,
                        decorationSize = 40.dp
                    )

                    Spacer(Modifier.width(16.dp))

                    Text(
                        text = selectedCard.cardType.asString(),
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 20.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.Normal,
                            color = Color(0xFF666666),
                        )
                    )

                    Spacer(Modifier.weight(1f))

                    TextButton(onClick = { onCardPickerClick() }) {
                        Text(
                            text = selectedCard.recentBalance, style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF100D40),
                            )
                        )

                        Spacer(Modifier.width(6.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_down_arrrow), contentDescription = "Drop down"
                        )
                    }
                }

                selectedCard == null -> {

                    Box(
                        Modifier
                            .dashedBorder(
                                strokeWidth = 2.dp,
                                color = MaterialTheme.colorScheme.primary,
                                cornerRadiusDp = 10.dp
                            )
                            .size(cardSize)
                    )

                    Spacer(Modifier.weight(1f))

                    TextButton(onClick = { onCardPickerClick() }) {
                        Text(
                            text = stringResource(R.string.choose_card), style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = primaryFontFamily,
                                fontWeight = FontWeight(600),
                                color = Color(0xFF100D40),
                            )
                        )

                        Spacer(Modifier.width(6.dp))

                        Icon(
                            painter = painterResource(id = R.drawable.ic_down_arrrow), contentDescription = "Drop down"
                        )
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun PanelCardSelector_Preview() {
    BankingAppTheme() {
        PanelCardPicker(
            selectedCard = CardUi.mock(), isLoading = false
        )
    }
}

@Composable
@Preview
fun PanelCardSelector_NotSelected_Preview() {
    BankingAppTheme() {
        PanelCardPicker(
            selectedCard = null, isLoading = false
        )
    }
}


@Composable
@Preview
fun PanelCardSelector_Loading_Preview() {
    BankingAppTheme() {
        PanelCardPicker(
            selectedCard = null, isLoading = true
        )
    }
}

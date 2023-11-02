package by.alexandr7035.banking.ui.feature_cards.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.dashedBorder
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun PanelCardPicker(
    isLoading: Boolean, selectedCard: CardUi?, onCardPickerClick: () -> Unit = {}
) {
    val shape = RoundedCornerShape(size = 10.dp)

    Row(
        modifier = Modifier
            .shadow(
                elevation = 32.dp,
                spotColor = Color.Gray,
                ambientColor = Color.Gray,
                shape = shape,
            )
            .background(color = Color(0xFFFFFFFF), shape = shape)
            .padding(16.dp), verticalAlignment = Alignment.CenterVertically
    ) {

        when {
            isLoading -> {

                SkeletonShape(
                    modifier = Modifier
                        .height(62.dp)
                        .width(94.dp)
                        .padding(end = 16.dp)
                )

                SkeletonShape(
                    modifier = Modifier
                        .height(14.dp)
                        .width(72.dp)
                )

                Spacer(
                    Modifier
                        .weight(1f)
                        .height(56.dp)
                )

                SkeletonShape(
                    modifier = Modifier
                        .height(24.dp)
                        .width(80.dp)
                        .padding(end = 12.dp)
                )
            }

            selectedCard != null -> {

                Box(
                    Modifier
                        .height(65.dp)
                )

                SmallCardIcon()

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
                        text = selectedCard.balance, style = TextStyle(
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
                        .height(62.dp)
                        .width(94.dp)
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

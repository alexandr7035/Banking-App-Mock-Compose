package by.alexandr7035.banking.ui.feature_savings.screen_saving_details

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.HorseshoeProgressIndicator
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.debug.debugPlaceholder
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.components.PaymentCard
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun SavingDetailsScreen(
    onBack: () -> Unit = {},
    onLinkedCardDetails: (id: String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            SecondaryToolBar(
                onBack = onBack,
                title = UiText.StringResource(R.string.your_saving)
            )
        }
    ) { pv ->
        BoxWithConstraints(Modifier.fillMaxSize()) {
            // TODO states
            SavingDetailsScreen_Ui(
                modifier = Modifier
                    .width(maxWidth)
                    .height(maxHeight)
                    .padding(pv),
                savingUi = SavingUi.mock(),
                cardUi = CardUi.mock(),
                onLinkedCardDetails = onLinkedCardDetails
            )
        }
    }
}

@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun SavingDetailsScreen_Ui(
    modifier: Modifier = Modifier,
    savingUi: SavingUi,
    cardUi: CardUi?,
    onLinkedCardDetails: (id: String) -> Unit = {}
) {
    Column(
        modifier = modifier.then(
            Modifier
                .verticalScroll(rememberScrollState())
                .padding(
                    vertical = 16.dp,
                    horizontal = 24.dp
                )
        ),
        horizontalAlignment = Alignment.Start,
    ) {

        BoxWithConstraints(
            Modifier
                .fillMaxWidth()
                .height(250.dp),
            contentAlignment = Alignment.Center
        ) {
            HorseshoeProgressIndicator(
                canvasSize = 300.dp,
                strokeWidth = 24.dp,
                modifier = Modifier.wrapContentHeight(
                    unbounded = true,
                    align = Alignment.Top
                ),
                value = savingUi.donePercentage,
                maxValue = 1f,
                innerComponent = {
                    InnerSavingText(savingUi)
                },
                foregroundIndicatorColor = MaterialTheme.colorScheme.primary,
                backgroundIndicatorColor = Color(0xFFF2F2F2)
            )
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val imageReq = ImageRequest.Builder(LocalContext.current)
                .data(savingUi.imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build()

            AsyncImage(
                model = imageReq,
                modifier = Modifier
                    .size(56.dp),
                contentDescription = null,
                placeholder = debugPlaceholder(debugPreview = R.drawable.ic_playstation)
            )

            Spacer(modifier = Modifier.width(20.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                Text(
                    text = savingUi.title, style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF100D40),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = savingUi.description,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal
                    ),
                    color = Color(0xFF100D40),
                )
            }
        }

        if (cardUi != null) {
            Spacer(Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Linked card", style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF100D40),
                    )
                )

                TextButton(
                    onClick = { },
                    enabled = false
                ) {
                    Text(
                        text = stringResource(R.string.edit), style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight(500),
                            color = Color(0xFF100D40),
                        )
                    )
                }
            }

            PaymentCard(
                cardUi = cardUi,
                onCLick = { onLinkedCardDetails.invoke(cardUi.cardNumber) }
            )
        }
    }
}

@Composable
private fun InnerSavingText(savingUi: SavingUi) {
    val roundedPercentage = floor(savingUi.donePercentage * 100).roundToInt()

    Text(
        text = "Progress",
        style = TextStyle(
            fontSize = 18.sp,
            fontFamily = primaryFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF4E4E4E),
        )
    )
    Text(
        text = "${roundedPercentage}%",
        style = TextStyle(
            fontSize = 32.sp,
            fontFamily = primaryFontFamily,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary,
        )
    )
}

@Preview
@Composable
fun SavingDetailsScreen_Preview() {
    ScreenPreview {
        SavingDetailsScreen_Ui(
            savingUi = SavingUi.mock(donePercentage = 0f),
            cardUi = CardUi.mock()
        )
    }
}
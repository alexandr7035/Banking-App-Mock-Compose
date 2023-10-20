package by.alexandr7035.banking.ui.feature_savings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.PercentageIndicator
import by.alexandr7035.banking.ui.components.debug.debugPlaceholder
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun SavingCard(
    modifier: Modifier = Modifier,
    savingUi: SavingUi,
    onClick: (savingId: String) -> Unit = {}
) {

    val shape = RoundedCornerShape(10.dp)

    Box(
        modifier = modifier.then(
            Modifier
                .shadow(
                    elevation = 32.dp,
                    spotColor = Color.Gray,
                    ambientColor = Color.Gray,
                    shape = shape,
                )
                .background(
                    color = Color.White, shape = shape
                )
                .clickable {
                    onClick.invoke("todo ID")
                }
                .padding(16.dp)
        )
    ) {
        Row(Modifier.height(IntrinsicSize.Max)) {
            val imageReq = ImageRequest.Builder(LocalContext.current)
                .data(savingUi.imageUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build()

            AsyncImage(
                model = imageReq,
                modifier = Modifier
                    .background(
                        color = Color(0xFFF2F2F2),
                        shape = CircleShape,
                    )
                    .size(48.dp)
                    .padding(8.dp),
                contentDescription = null,
                placeholder = debugPlaceholder(debugPreview = R.drawable.ic_home)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f, fill = true),
            ) {
                Text(
                    text = savingUi.title, style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF100D40),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = savingUi.description,
                    style = TextStyle(
                        fontSize = 12.sp, fontFamily = primaryFontFamily, fontWeight = FontWeight.Normal
                    ),
                    color = Color(0xFF100D40),
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            PercentageIndicator(
                percentage = savingUi.donePercentage,
                modifier = Modifier.size(48.dp)
            )
        }
    }
}

@Composable
@Preview
fun SavingCard_Preview() {
    BankingAppTheme() {
        SavingCard(
            savingUi = SavingUi.mock()
        )
    }
}
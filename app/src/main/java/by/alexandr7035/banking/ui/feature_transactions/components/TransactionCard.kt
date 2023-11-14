package by.alexandr7035.banking.ui.feature_transactions.components

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.components.debug.debugPlaceholder
import by.alexandr7035.banking.ui.feature_transactions.model.TransactionUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun TransactionCard(
    modifier: Modifier = Modifier, transactionUi: TransactionUi
) {

    val shape = RoundedCornerShape(10.dp)

    Box(modifier = modifier.then(Modifier
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
//                    onClick.invoke(savingUi.id)
        }
        .padding(16.dp))) {

        when (transactionUi.type) {
            TransactionType.TOP_UP -> TopUpTransactionCard(transactionUi = transactionUi)
            else -> ContactTransactionCard(transactionUi = transactionUi)
        }

    }
}

@Composable
private fun ContactTransactionCard(
    transactionUi: TransactionUi
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max), verticalAlignment = Alignment.CenterVertically
    ) {
        // TODO placeholder
        val imageReq =
            ImageRequest.Builder(LocalContext.current).data(transactionUi.contact?.profilePictureUrl).decoderFactory(SvgDecoder.Factory())
                .crossfade(true).build()

        Box() {
            AsyncImage(
                model = imageReq, modifier = Modifier
                    .background(
                        color = Color(0xFFF2F2F2),
                        shape = CircleShape,
                    )
                    .size(48.dp)
                    .padding(8.dp), contentDescription = null, placeholder = debugPlaceholder(debugPreview = R.drawable.ic_home)
            )

            Image(
                modifier = Modifier
                    .size(16.dp)
                    .offset(0.dp, -4.dp)
                    .align(Alignment.TopEnd),
                painter = getTxStatusMark(transactionUi),
                contentDescription = "${transactionUi.status}"
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f, fill = true),
        ) {

            Text(
                text = transactionUi.contact?.name ?: stringResource(R.string.unknown_user), style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333),
                )
            )

            Text(
                text = transactionUi.transactionDate, style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF100D40),
                )
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = getTxValueWithPrefix(transactionUi), style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF100D40),
            )
        )
    }
}

@Composable
private fun TopUpTransactionCard(
    transactionUi: TransactionUi,
) {
    Row(
        modifier = Modifier.height(IntrinsicSize.Max), verticalAlignment = Alignment.CenterVertically
    ) {

        Box() {
            Image(
                painter = painterResource(id = R.drawable.ic_topup_arrow),
                contentDescription = "TopUp transaction",
                modifier = Modifier
                    .background(
                        color = Color(0xFFF2F2F2),
                        shape = CircleShape,
                    )
                    .size(48.dp)
                    .padding(8.dp)
            )

            Image(
                modifier = Modifier
                    .size(16.dp)
                    .offset(0.dp, -4.dp)
                    .align(Alignment.TopEnd),
                painter = getTxStatusMark(transactionUi),
                contentDescription = "${transactionUi.status}"
            )
        }



        Spacer(modifier = Modifier.width(16.dp))

        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxHeight()
                .weight(1f, fill = true),
        ) {
            Text(
                text = stringResource(id = R.string.top_up), style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333),
                )
            )

            Text(
                text = transactionUi.transactionDate, style = TextStyle(
                    fontSize = 12.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF100D40),
                )
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = getTxValueWithPrefix(transactionUi), style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF100D40),
            )
        )
    }
}

@Composable
@Preview
fun TransactionCardUi_Preview() {
    BankingAppTheme() {
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            TransactionCard(
                transactionUi = TransactionUi.mock(
                    type = TransactionType.TOP_UP, status = TransactionStatus.PENDING
                )
            )

            TransactionCard(
                transactionUi = TransactionUi.mock(
                    type = TransactionType.SEND, status = TransactionStatus.COMPLETED
                )
            )

            TransactionCard(
                transactionUi = TransactionUi.mock(
                    type = TransactionType.RECEIVE, status = TransactionStatus.FAILED
                )
            )

        }
    }
}


private fun getTxValueWithPrefix(
    transactionUi: TransactionUi
): String {
    return when (transactionUi.type) {
        TransactionType.SEND -> "-${transactionUi.value.amountStr}"
        TransactionType.RECEIVE -> "+${transactionUi.value.amountStr}"
        TransactionType.TOP_UP -> "+${transactionUi.value.amountStr}"
    }
}

@Composable
private fun getTxStatusMark(transactionUi: TransactionUi): Painter {
    val res = when (transactionUi.status) {
        TransactionStatus.PENDING -> R.drawable.ic_status_pending
        TransactionStatus.COMPLETED -> R.drawable.ic_status_accepted
        TransactionStatus.FAILED -> R.drawable.ic_status_rejected
    }

    return painterResource(id = res)
}
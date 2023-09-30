package by.alexandr7035.banking.ui.feature_home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun AccountActionPanel(
    balance: Float,
    onActionClick: (action: AccountAction) -> Unit
) {

    val shape = RoundedCornerShape(size = 10.dp)
    Column(
        modifier = Modifier
            .shadow(
                elevation = 32.dp,
                spotColor = Color.Gray,
                ambientColor = Color.Gray,
                shape = shape,
            )
            .background(color = Color(0xFFFFFFFF), shape = shape)
    ) {
        Row(Modifier.padding(horizontal = 24.dp, vertical = 18.dp)) {
            Text(
                text = "My Balance",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF100D40),
                )
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "\$${balance}",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF100D40),
                )
            )
        }

        Divider(
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = Color(0xFFF2F2F2)
        )


        BoxWithConstraints(Modifier.fillMaxWidth()) {

            Row(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                    .width(maxWidth)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                val items = listOf(
                    AccountAction.SendMoney,
                    AccountAction.RequestMoney,
                    AccountAction.Pay,
                    AccountAction.TopUp
                )

                items.forEach { it ->
                    AccountActionItem(actionType = it, onActionClick = { accountAction ->
                        onActionClick.invoke(accountAction)
                    })
                }
            }
        }

    }
}

@Composable
private fun AccountActionItem(
    actionType: AccountAction,
    onActionClick: (action: AccountAction) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .clickable(
                onClick = {
                    onActionClick.invoke(actionType)
                },
                indication = rememberRipple(radius = 48.dp, bounded = false),
                interactionSource = interactionSource
            )
            .padding(vertical = 8.dp, horizontal = 12.dp)
            .clip(RoundedCornerShape(14.dp))

    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    Color(0xFFECE7FF),
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = actionType.icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(actionType.uiTitle),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight(400),
                color = Color(0xFF100D40),
                textAlign = TextAlign.Center,
            )
        )
    }
}

@Preview
@Composable
fun AccountActionPanel_Preview() {
    AccountActionPanel(balance = 2000.52F, onActionClick = {})
}
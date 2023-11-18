package by.alexandr7035.banking.ui.feature_account.components.account_actions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun AccountActionRow(
    modifier: Modifier = Modifier,
    onActionClick: (action: AccountAction) -> Unit = {},
    paddingValues: PaddingValues = PaddingValues()
) {
    Row(
        modifier = modifier.then(
            Modifier
                .padding(paddingValues)
                .horizontalScroll(rememberScrollState())
        ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val items = listOf(
            AccountAction.SendMoney,
            AccountAction.TopUp,
            AccountAction.RequestMoney,
            AccountAction.Pay,
        )

        val available = listOf(
            AccountAction.SendMoney,
            AccountAction.TopUp,
        )

        items.forEach {
            AccountActionItem(
                action = it,
                onActionClick = { accountAction ->
                    onActionClick(accountAction)
                },
                isAvailable = it in available
            )
        }
    }
}

@Composable
private fun AccountActionItem(
    action: AccountAction,
    isAvailable: Boolean,
    onActionClick: (action: AccountAction) -> Unit
) {

    val interactionSource = remember { MutableInteractionSource() }

    val clickableModifier = if (isAvailable) {
        Modifier.clickable(
            onClick = {
                onActionClick(action)
            },
            indication = rememberRipple(radius = 48.dp, bounded = false),
            interactionSource = interactionSource
        )
    } else {
        Modifier
    }

    val bgColor = if (isAvailable) {
        Color(0xFFECE7FF)
    }
    else {
        Color(0xFFDEDEDE)
    }

    val fgColorFilter = if (isAvailable) null else ColorFilter.tint(Color(0xFF858585))


    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize()
            .then(clickableModifier)
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    bgColor,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = action.icon),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                colorFilter = fgColorFilter
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = stringResource(action.uiTitle),
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 20.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF100D40),
                textAlign = TextAlign.Center,
            )
        )
    }
}
package by.alexandr7035.banking.ui.feature_home.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.cards.PrimaryCard
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi
import by.alexandr7035.banking.ui.feature_account.components.account_actions.AccountAction
import by.alexandr7035.banking.ui.feature_account.components.account_actions.AccountActionRow
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun AccountActionPanel(
    balanceFlow: Flow<MoneyAmountUi?>,
    onActionClick: (action: AccountAction) -> Unit
) {
    val balance = balanceFlow.collectAsStateWithLifecycle(initialValue = null).value

    PrimaryCard {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.my_balance),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF100D40),
                    )
                )

                Spacer(Modifier.weight(1f))

                if (balance != null) {
                    Text(
                        text = balance.amountStr,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = primaryFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF100D40),
                        )
                    )
                } else {
                    SkeletonShape(
                        modifier = Modifier
                            .width(100.dp)
                            .height(18.dp),
                        shape = RoundedCornerShape(4.dp)
                    )
                }
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 2.dp,
                color = Color(0xFFF2F2F2)
            )


            BoxWithConstraints(Modifier.fillMaxWidth()) {
                AccountActionRow(
                    modifier = Modifier.width(maxWidth),
                    paddingValues = PaddingValues(
                        top = 16.dp,
                        bottom = 8.dp,
                        start = 12.dp,
                        end = 12.dp
                    ),
                    onActionClick = onActionClick
                )
            }
        }

    }

}

@Composable
fun AccountActionPanel_Skeleton() {

    PrimaryCard {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.my_balance),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF100D40),
                    )
                )

                Spacer(Modifier.weight(1f))

                SkeletonShape(
                    modifier = Modifier
                        .width(100.dp)
                        .height(18.dp),
                    shape = RoundedCornerShape(4.dp)
                )
            }

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                thickness = 2.dp,
                color = Color(0xFFF2F2F2)
            )

            SkeletonShape(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 8.dp, start = 12.dp, end = 12.dp)
                    .height(100.dp),
                shape = RoundedCornerShape(4.dp)
            )
        }
    }
}


@Preview
@Composable
fun AccountActionPanel_Preview() {
    AccountActionPanel(balanceFlow = flowOf(MoneyAmountUi("$2000"))) {}
}

@Preview
@Composable
fun AccountActionPanel_Skeleton_Preview() {
    AccountActionPanel_Skeleton()
}
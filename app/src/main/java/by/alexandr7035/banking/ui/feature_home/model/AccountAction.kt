package by.alexandr7035.banking.ui.feature_home.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import by.alexandr7035.banking.R

sealed class AccountAction(@DrawableRes val icon: Int, @StringRes val uiTitle: Int) {
    object SendMoney: AccountAction(R.drawable.ic_send, R.string.send)
    object RequestMoney: AccountAction(R.drawable.ic_request, R.string.request)
    object TopUp: AccountAction(R.drawable.ic_topup, R.string.top_up)
    object Pay: AccountAction(R.drawable.ic_pay, R.string.pay)
}

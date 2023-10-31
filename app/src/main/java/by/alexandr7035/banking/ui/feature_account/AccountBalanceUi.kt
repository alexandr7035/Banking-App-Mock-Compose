package by.alexandr7035.banking.ui.feature_account

import by.alexandr7035.banking.domain.features.account.model.AccountBalance
import by.alexandr7035.banking.domain.features.account.model.BalanceCurrency
import kotlin.math.roundToInt

data class AccountBalanceUi(
    val balanceStr: String
) {
    companion object {
        fun mapFromDomain(accountBalance: AccountBalance): AccountBalanceUi {
            val roundedBalance = (accountBalance.balanceValue * 100.0).roundToInt() / 100.0
            val balanceStr = "$roundedBalance"

            return when (accountBalance.currency) {
                BalanceCurrency.USD -> {
                    AccountBalanceUi("$$balanceStr")
                }
            }
        }
    }
}

package by.alexandr7035.banking.ui.feature_account

import by.alexandr7035.banking.domain.features.account.model.BalanceCurrency
import by.alexandr7035.banking.domain.features.account.model.BalanceValue
import kotlin.math.roundToInt

data class BalanceValueUi(
    val balanceStr: String,
) {
    companion object {
        fun mapFromDomain(balance: BalanceValue): BalanceValueUi {
            val balanceStr = when (balance) {
                is BalanceValue.DoubleBalance -> {
                    val rounded = (balance.value * 100.0).roundToInt() / 100.0
                    "$rounded"
                }
                is BalanceValue.LongBalance -> {
                    "${balance.value}"
                }
            }

            // Add currency prefixes
            return when (balance.currency) {
                BalanceCurrency.USD -> {
                    BalanceValueUi("$$balanceStr")
                }
            }
        }
    }
}

package by.alexandr7035.banking.ui.feature_account

import by.alexandr7035.banking.domain.features.account.model.BalanceCurrency
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

data class BalanceValueUi(
    val balanceStr: String,
) {
    companion object {
        fun mapFromDomain(balance: MoneyAmount): BalanceValueUi {
            val symbols = DecimalFormatSymbols(Locale.US)
            val decimalFormat = DecimalFormat("#,##0.##", symbols)
            decimalFormat.isGroupingUsed = false
            val formattedValue = decimalFormat.format(balance.value)


            // Add currency prefixes
            return when (balance.currency) {
                BalanceCurrency.USD -> {
                    BalanceValueUi("$$formattedValue")
                }
            }
        }
    }
}

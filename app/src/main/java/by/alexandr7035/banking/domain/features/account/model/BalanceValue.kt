package by.alexandr7035.banking.domain.features.account.model

sealed class BalanceValue(
    open val currency: BalanceCurrency
) {
    data class LongBalance(
        val value: Long,
        override val currency: BalanceCurrency = BalanceCurrency.USD
    ) : BalanceValue(currency)

    data class DoubleBalance(
        val value: Double,
        override val currency: BalanceCurrency = BalanceCurrency.USD
    ) : BalanceValue(currency)
}
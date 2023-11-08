package by.alexandr7035.banking.domain.features.account.model

data class AccountBalance(
    val balanceValue: Double,
    val currency: BalanceCurrency = BalanceCurrency.USD
)

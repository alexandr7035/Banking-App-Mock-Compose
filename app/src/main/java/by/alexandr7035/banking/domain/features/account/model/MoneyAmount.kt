package by.alexandr7035.banking.domain.features.account.model

// Wrapper class for money representation
// Used to encapsulation of chosen base types (Double, BigDecimal and so on)
data class MoneyAmount(
    val value: Float,
    val currency: BalanceCurrency = BalanceCurrency.USD,
) {
    operator fun plus(other: MoneyAmount): MoneyAmount {
        return this.copy(value = this.value + other.value)
    }

    operator fun minus(other: MoneyAmount): MoneyAmount {
        return this.copy(value = this.value - other.value)
    }

    operator fun compareTo(other: MoneyAmount): Int {
        return this.value.compareTo(other.value)
    }
}
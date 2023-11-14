package by.alexandr7035.banking.data.transactions

import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

sealed class TransactionFilterParams {
    data class ByType(val type: TransactionType): TransactionFilterParams()
}

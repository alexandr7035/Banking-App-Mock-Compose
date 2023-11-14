package by.alexandr7035.banking.ui.feature_transactions

import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

sealed class TransactionHistoryIntent {
    object InitLoad: TransactionHistoryIntent()
    data class ChangeTransactionFilter(val filterByType: TransactionType?): TransactionHistoryIntent()
}

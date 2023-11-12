package by.alexandr7035.banking.ui.feature_transactions

import by.alexandr7035.banking.ui.feature_transactions.model.TransactionUi

data class TransactionHistoryState(
    val isLoading: Boolean = true,
    val transactions: List<TransactionUi> = emptyList()
)

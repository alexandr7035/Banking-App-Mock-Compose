package by.alexandr7035.banking.ui.feature_transactions

sealed class TransactionHistoryIntent {
    object InitialLoad: TransactionHistoryIntent()
}

package by.alexandr7035.banking.ui.feature_transactions

import androidx.paging.PagingData
import by.alexandr7035.banking.ui.feature_transactions.model.TransactionUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class TransactionHistoryState(
    val transactionsPagingState: Flow<PagingData<TransactionUi>> = MutableStateFlow(PagingData.empty())
)

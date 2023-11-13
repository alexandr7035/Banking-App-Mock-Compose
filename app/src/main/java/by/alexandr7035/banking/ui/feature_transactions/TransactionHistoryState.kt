package by.alexandr7035.banking.ui.feature_transactions

import androidx.paging.PagingData
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class TransactionHistoryState(
    val isLoading: Boolean = true,
    val transactionsPagingState: Flow<PagingData<Transaction>> = MutableStateFlow(PagingData.empty())
)

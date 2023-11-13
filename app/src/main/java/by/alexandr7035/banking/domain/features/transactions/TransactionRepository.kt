package by.alexandr7035.banking.domain.features.transactions

import androidx.paging.PagingData
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    fun getTransactions(): Flow<PagingData<Transaction>>
}
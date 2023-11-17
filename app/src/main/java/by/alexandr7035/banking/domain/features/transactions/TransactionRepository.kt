package by.alexandr7035.banking.domain.features.transactions

import androidx.paging.PagingData
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionRowPayload
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>>
    fun getTransactionStatusFlow(transactionId: Long): Flow<TransactionStatus>
    suspend fun submitTransaction(payload: TransactionRowPayload)
}
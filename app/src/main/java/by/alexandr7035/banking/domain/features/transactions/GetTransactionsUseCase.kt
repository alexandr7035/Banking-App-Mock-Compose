package by.alexandr7035.banking.domain.features.transactions

import androidx.paging.PagingData
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(filterByType: TransactionType?): Flow<PagingData<Transaction>> {
        return transactionRepository.getTransactions(filterByType)
    }
}
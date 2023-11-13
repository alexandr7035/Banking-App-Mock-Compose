package by.alexandr7035.banking.domain.features.transactions

import androidx.paging.PagingData
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {
    fun execute(): Flow<PagingData<Transaction>> {
        return transactionRepository.getTransactions()
    }
}
package by.alexandr7035.banking.data.transactions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.alexandr7035.banking.data.transactions.db.TransactionDao
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.transactions.TransactionRepository
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class TransactionRepositoryMock(
    private val transactionDao: TransactionDao,
    private val coroutineDispatcher: CoroutineDispatcher
) : TransactionRepository {
    override fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_MAX_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                TransactionSource(
                    filterByType = filterByType,
                    transactionDao = transactionDao
                )
            }
        ).flow
    }

    override fun getTransactionStatusFlow(transactionId: Long): Flow<TransactionStatus> {
        return flow {
            val tx = transactionDao.getTransaction(transactionId) ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)

            // Emit last cached status
            while (true) {
                emit(tx.recentStatus)
                delay(MOCK_TRANSACTION_STATUS_CHECK_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    companion object {
        private const val PAGE_MAX_SIZE = 5
        private const val PREFETCH_DISTANCE = 1
        private const val MOCK_TRANSACTION_STATUS_CHECK_DELAY = 5000L
    }
}
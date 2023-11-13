package by.alexandr7035.banking.data.transactions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.alexandr7035.banking.domain.features.transactions.TransactionRepository
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryMock(
    // TODO dao
): TransactionRepository {
    override fun getTransactions(): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_MAX_SIZE,
                prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = {
                TransactionSource()
            }
        ).flow
    }

    companion object {
        private const val PAGE_MAX_SIZE = 20
        private const val PREFETCH_DISTANCE = 2
    }
}
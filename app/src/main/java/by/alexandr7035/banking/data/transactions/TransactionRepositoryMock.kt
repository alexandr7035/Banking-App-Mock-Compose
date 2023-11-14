package by.alexandr7035.banking.data.transactions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import by.alexandr7035.banking.domain.features.transactions.TransactionRepository
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionRepositoryMock(
    // TODO dao
): TransactionRepository {
    override fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_MAX_SIZE,
                prefetchDistance = PREFETCH_DISTANCE),
            pagingSourceFactory = {
                TransactionSource(filterByType)
            }
        ).flow
    }

    companion object {
        private const val PAGE_MAX_SIZE = 5
        private const val PREFETCH_DISTANCE = 1
    }
}
package by.alexandr7035.banking.data.transactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.alexandr7035.banking.data.transactions.db.TransactionDao
import by.alexandr7035.banking.data.transactions.db.TransactionEntity
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

class TransactionSource(
    // Add more advanced filters / sorting if necessary
    private val transactionDao: TransactionDao,
    private val filterByType: TransactionType?,
) : PagingSource<Int, TransactionEntity>() {
    override fun getRefreshKey(state: PagingState<Int, TransactionEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionEntity> {
        return try {
            val currentPage = params.key ?: 1

            val transactions = loadFromDbCache(
                filterByType = filterByType,
                params = params
            )

            LoadResult.Page(
                data = transactions,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (transactions.size < params.loadSize) null else currentPage + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private suspend fun loadFromDbCache(
        filterByType: TransactionType?,
        params: LoadParams<Int>,
    ): List<TransactionEntity> {
        val currentPage = params.key ?: 1
        val startPosition = (currentPage - 1) * params.loadSize

        return when (filterByType) {
            null -> transactionDao.getTransactionList(
                startPosition = startPosition,
                loadSize = params.loadSize
            )

            else -> transactionDao.getTransactionList(
                filterType = filterByType,
                startPosition = startPosition,
                loadSize = params.loadSize
            )
        }
    }
}
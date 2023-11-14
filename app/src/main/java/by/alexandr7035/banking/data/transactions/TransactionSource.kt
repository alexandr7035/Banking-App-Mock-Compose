package by.alexandr7035.banking.data.transactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.alexandr7035.banking.data.transactions.db.TransactionDao
import by.alexandr7035.banking.data.transactions.db.TransactionEntity
import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

class TransactionSource(
    // Add more advanced filters / sorting if necessary
    private val transactionDao: TransactionDao,
    private val filterByType: TransactionType?
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currentPage = params.key ?: 1

            val transactions = loadFromDbCache(
                filterByType = filterByType,
                params = params
            )

            LoadResult.Page(
                data = transactions,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                // TODO check
                nextKey = if (transactions.isEmpty() || transactions.size < params.loadSize) null else currentPage + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private suspend fun loadFromDbCache(
        filterByType: TransactionType?,
        params: LoadParams<Int>,
    ): List<Transaction> {
        val currentPage = params.key ?: 1
        val startPosition = (currentPage - 1) * params.loadSize

        return when (filterByType) {
            null -> transactionDao.getTransactionFromCacheWithPagination(
                startPosition = startPosition,
                loadSize = params.loadSize
            ).map {
                mapTransactionFromDb(it)
            }

            else -> transactionDao.getTransactionFromCacheByTypeWithPagination(
                filterType = filterByType,
                startPosition = startPosition,
                loadSize = params.loadSize
            ).map {
                mapTransactionFromDb(it)
            }
        }
    }

    private fun mapTransactionFromDb(entity: TransactionEntity): Transaction {
        return Transaction(
            id = entity.id,
            type = entity.type,
            value = entity.value,
            recentStatus = entity.recentStatus,
            // TODO getting contact
            linkedContact = when (entity.type) {
                TransactionType.TOP_UP -> null
                else -> Contact(
                    id = "0000",
                    name = "Test name ${entity.type}",
                    profilePic = "todo"
                )
            },
            createdDate = entity.createdDate,
            updatedStatusDate = entity.updatedStatusDate
        )
    }
}
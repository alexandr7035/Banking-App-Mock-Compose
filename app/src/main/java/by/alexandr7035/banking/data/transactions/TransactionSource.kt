package by.alexandr7035.banking.data.transactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import kotlinx.coroutines.delay

class TransactionSource(): PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currentPage = params.key ?: 1

            if (currentPage > 1) {
                throw AppError(ErrorType.UNKNOWN_ERROR)
            }

            delay(3000)
            val transactions = List(params.loadSize) {
                Transaction(
                    id = 0,
                )
            }

            LoadResult.Page(
                data = transactions,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                // TODO check
                nextKey = if (transactions.isEmpty()) null else currentPage + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}
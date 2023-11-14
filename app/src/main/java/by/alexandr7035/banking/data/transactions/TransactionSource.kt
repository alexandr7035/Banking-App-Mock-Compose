package by.alexandr7035.banking.data.transactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import kotlinx.coroutines.delay

class TransactionSource(
    // Add more advanced filters / sorting if necessary
    private val filterByType: TransactionType?
) : PagingSource<Int, Transaction>() {
    override fun getRefreshKey(state: PagingState<Int, Transaction>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Transaction> {
        return try {
            val currentPage = params.key ?: 1
            delay(100)

            val transactions = if (currentPage > 1) {
                emptyList()
            }
            else {
                loadFromDbCache(
                    filterByType = filterByType,
                    params = params
                )
            }

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

    private fun loadFromDbCache(
        filterByType: TransactionType?,
        params: LoadParams<Int>
    ): List<Transaction> {
        val transactions = when (filterByType) {
            null -> {
                List(3) {
                    Transaction(
                        id = 0,
                        linkedContact = Contact(
                            id = "0000",
                            name = "All test",
                            profilePic = ""
                        ),
                        type = TransactionType.SEND,
                        value = MoneyAmount(250F)
                    )
                }
            }

            TransactionType.SEND -> {
                List(2) {

                    Transaction(
                        id = 0,
                        linkedContact = Contact(
                            id = "0000",
                            name = "Send test",
                            profilePic = ""
                        ),
                        type = TransactionType.SEND,
                        value = MoneyAmount(250F)
                    )
                }
            }

            TransactionType.RECEIVE -> {
                List(1) {
                    Transaction(
                        id = 0,
                        linkedContact = Contact(
                            id = "0000",
                            name = "Receive test",
                            profilePic = ""
                        ),
                        type = TransactionType.SEND,
                        value = MoneyAmount(250F)
                    )
                }
            }

            TransactionType.TOP_UP -> {
                emptyList()
            }
        }

        return transactions
    }
}
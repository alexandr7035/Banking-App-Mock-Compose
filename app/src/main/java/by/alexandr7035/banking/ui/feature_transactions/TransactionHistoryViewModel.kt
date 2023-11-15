package by.alexandr7035.banking.ui.feature_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.transactions.GetTransactionsUseCase
import by.alexandr7035.banking.domain.features.transactions.ObserveTransactionStatusUseCase
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.feature_transactions.model.TransactionUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionHistoryViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase,
    private val observeTransactionStatusUseCase: ObserveTransactionStatusUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TransactionHistoryState())
    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        reduceError(ErrorType.fromThrowable(throwable))
    }

    fun emitIntent(intent: TransactionHistoryIntent) {
        when (intent) {
            is TransactionHistoryIntent.InitLoad -> {
                loadTransactions()
            }

            is TransactionHistoryIntent.ChangeTransactionFilter -> {
                loadTransactions(intent.filterByType)
            }
        }
    }

    private fun loadTransactions(filterType: TransactionType? = null) {
        viewModelScope.launch(errorHandler) {
            val listFlow = getTransactionsUseCase.execute(filterType)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.map { tx ->
                       TransactionUi.mapFromDomain(
                           transaction = tx,
                           statusFlow = observeTransactionStatusUseCase.execute(tx.id)
                       )
                    }
                }
                .catch {
                    reduceError(ErrorType.fromThrowable(it))
                }

            _state.update {
                it.copy(
                    transactionsPagingState = listFlow)
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        // TODO
    }
}
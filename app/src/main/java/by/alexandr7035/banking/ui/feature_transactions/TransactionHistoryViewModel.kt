package by.alexandr7035.banking.ui.feature_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.transactions.GetTransactionsUseCase
import by.alexandr7035.banking.domain.features.transactions.ObserveTransactionStatusUseCase
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_transactions.model.TransactionUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
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
        _state.update {
            it.copy(
                transactionsPagingState = MutableStateFlow(PagingData.empty()),
                screenError = null
            )
        }

        viewModelScope.launch(errorHandler) {
            val listFlow = getTransactionsUseCase.execute(filterType)
                .distinctUntilChanged()
                .cachedIn(viewModelScope)
                .map { pagingData ->
                    pagingData.map { tx ->
                        val statusFlowRes = OperationResult.runWrapped {
                            observeTransactionStatusUseCase.execute(tx.id)
                        }

                        val txStatusFlow = when (statusFlowRes) {
                            is OperationResult.Failure -> {
                                reduceError(statusFlowRes.error.errorType)
                                emptyFlow()
                            }
                            is OperationResult.Success -> statusFlowRes.data
                        }

                        TransactionUi.mapFromDomain(
                            transaction = tx,
                            statusFlow = txStatusFlow.catch { e ->
                                reduceError(ErrorType.fromThrowable(e))
                            }
                        )
                    }
                }
            _state.update {
                it.copy(
                    transactionsPagingState = listFlow
                )
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        _state.update {
            it.copy(screenError = error.asUiTextError())
        }
    }
}
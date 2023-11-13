package by.alexandr7035.banking.ui.feature_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.transactions.GetTransactionsUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionHistoryViewModel(
    private val getTransactionsUseCase: GetTransactionsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TransactionHistoryState())
    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        reduceError(ErrorType.fromThrowable(throwable))
    }

    fun emitIntent(intent: TransactionHistoryIntent) {
        when (intent) {
            TransactionHistoryIntent.InitLoad -> {
                viewModelScope.launch(errorHandler) {
                    val listFlow = getTransactionsUseCase.execute()
                        .distinctUntilChanged()
                        .cachedIn(viewModelScope)
                        .catch {
                            reduceError(ErrorType.fromThrowable(it))
                        }

                    _state.update {
                        it.copy(
                            isLoading = false,
                            transactionsPagingState = listFlow)
                    }

//                        .collect { pagingData ->
//                            _state.update {
//                                it.copy(
//                                    transactionsPagingState = pagingData
//                                )
//                            }
//                        }
                }
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        // TODO
    }
}
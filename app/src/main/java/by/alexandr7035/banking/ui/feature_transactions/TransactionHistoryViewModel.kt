package by.alexandr7035.banking.ui.feature_transactions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.ui.feature_transactions.model.TransactionUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionHistoryViewModel: ViewModel() {
    private val _state = MutableStateFlow(TransactionHistoryState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: TransactionHistoryIntent) {
        when (intent) {
            TransactionHistoryIntent.InitialLoad -> {
                viewModelScope.launch {
                    delay(1000)

                    _state.update {
                        it.copy(
                            isLoading = false,
                            transactions = List(5) { TransactionUi.mock() })
                    }
                }
            }
        }
    }
}
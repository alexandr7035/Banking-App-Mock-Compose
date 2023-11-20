package by.alexandr7035.banking.ui.feature_qr_codes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DisplayQrViewModel: ViewModel() {
    private val _state = MutableStateFlow(DisplayQrState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: DisplayQrIntent) {
        when (intent) {
            is DisplayQrIntent.GenerateQr -> {
                _state.update {
                    it.copy(isLoading = true)
                }

                viewModelScope.launch {
                    delay(1000)
                    _state.update {
                        it.copy(
                            isLoading = false,
                            qrString = "Test Test",
                        )
                    }
                }
            }
        }
    }
}
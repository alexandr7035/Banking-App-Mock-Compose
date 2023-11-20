package by.alexandr7035.banking.ui.feature_qr_codes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.qr_codes.GenerateQrCodeUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DisplayQrViewModel(
    private val generateQrCodeUseCase: GenerateQrCodeUseCase
): ViewModel() {
    private val _state = MutableStateFlow(DisplayQrState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: DisplayQrIntent) {
        when (intent) {
            is DisplayQrIntent.GenerateQr -> {
                _state.update {
                    it.copy(isLoading = true)
                }

                viewModelScope.launch {
                    val qrRes = OperationResult.runWrapped {
                        generateQrCodeUseCase.execute(intent.qrPurpose)
                    }

                    when (qrRes) {
                        is OperationResult.Failure -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    error = qrRes.error.errorType.asUiTextError()
                                )
                            }
                        }
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    qrString = qrRes.data
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
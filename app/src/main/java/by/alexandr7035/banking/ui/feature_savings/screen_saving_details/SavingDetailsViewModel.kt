package by.alexandr7035.banking.ui.feature_savings.screen_saving_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.cards.model.PaymentCard
import by.alexandr7035.banking.domain.features.savings.model.Saving
import by.alexandr7035.banking.domain.features.cards.GetCardByIdUseCase
import by.alexandr7035.banking.domain.features.savings.GetSavingByIdUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SavingDetailsViewModel(
    private val getSavingByIdUseCase: GetSavingByIdUseCase,
    private val getCardByIdUseCase: GetCardByIdUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<SavingDetailsState> = MutableStateFlow(SavingDetailsState.Loading)
    val state = _state.asStateFlow()

    fun emitIntent(intent: SavingDetailsIntent) {
        when (intent) {
            is SavingDetailsIntent.EnterScreen -> {
                reduceLoading()

                viewModelScope.launch() {
                    val savingRes = OperationResult.runWrapped {
                        getSavingByIdUseCase.execute(intent.savingId)
                    }

                    when (savingRes) {
                        is OperationResult.Success -> {
                            reduceData(savingRes.data)

                            if (savingRes.data.linkedCardId != null) {
                                emitIntent(SavingDetailsIntent.LoadLinkedCard(savingRes.data.linkedCardId))
                            }
                        }

                        is OperationResult.Failure -> {
                            reduceError(savingRes.error.errorType)
                        }
                    }
                }
            }

            is SavingDetailsIntent.LoadLinkedCard -> {
                reduceCardLoading(isLoading = true)

                viewModelScope.launch {
                    // A little longer delay for demo purpose
                    delay(300L)

                    val cardRes = OperationResult.runWrapped {
                        getCardByIdUseCase.execute(intent.cardId)
                    }

                    when (cardRes) {
                        is OperationResult.Success -> {
                            reduceCardData(cardRes.data)
                        }

                        // Do not show errors as card is secondary in use case
                        is OperationResult.Failure -> {
                            reduceCardLoading(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    private fun reduceLoading() {
        _state.value = SavingDetailsState.Loading
    }

    private fun reduceData(saving: Saving) {
        _state.value = SavingDetailsState.Success(
            saving = SavingUi.mapFromDomain(saving),
            isCardLoading = saving.linkedCardId != null
        )
    }

    private fun reduceError(errorType: ErrorType) {
        _state.value = SavingDetailsState.Error(error = errorType.asUiTextError())
    }

    private fun reduceCardLoading(isLoading: Boolean) {
        val currentState = _state.value

        if (currentState is SavingDetailsState.Success) {
            _state.value = currentState.copy(isCardLoading = isLoading)
        }
    }

    private fun reduceCardData(card: PaymentCard) {
        val currentState = _state.value

        if (currentState is SavingDetailsState.Success) {
            _state.value = currentState.copy(
                isCardLoading = false,
                linkedCard = CardUi.mapFromDomain(card)
            )
        }
    }
}
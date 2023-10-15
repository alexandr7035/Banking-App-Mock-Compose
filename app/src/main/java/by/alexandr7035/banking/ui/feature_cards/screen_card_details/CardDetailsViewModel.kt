package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.repository.cards.PaymentCard
import by.alexandr7035.banking.domain.usecases.cards.GetCardByNumberUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val getCardByNumberUseCase: GetCardByNumberUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState.Loading)
    val state = _state.asStateFlow()

    fun emitIntent(intent: CardDetailsIntent) {
        when (intent) {
            is CardDetailsIntent.EnterScreen -> {
                reduceScreenLoading()

                viewModelScope.launch {
                    val cardResult = OperationResult.runWrapped {
                        getCardByNumberUseCase.execute(intent.cardNumber)
                    }

                    when (cardResult) {
                        is OperationResult.Success -> {
                            reduceSuccessData(cardResult.data)
                        }

                        is OperationResult.Failure -> {
                            reduceError(cardResult.error.errorType)
                        }
                    }

                }
            }

            CardDetailsIntent.RequestDeleteCard -> TODO()
            CardDetailsIntent.ConfirmDeleteCard -> TODO()
            CardDetailsIntent.ConsumeCardDeletedEvent -> TODO()
        }
    }


    private fun reduceScreenLoading() {
        _state.value = CardDetailsState.Loading
    }

    private fun reduceSuccessData(card: PaymentCard) {
        _state.value = CardDetailsState.Success(card = CardUi.mapFromDomain(card))
    }

    private fun reduceError(error: ErrorType) {
        _state.value = CardDetailsState.Error(error = error.asUiTextError())
    }
}
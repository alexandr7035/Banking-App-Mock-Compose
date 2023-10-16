package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.repository.cards.PaymentCard
import by.alexandr7035.banking.domain.usecases.cards.GetCardByNumberUseCase
import by.alexandr7035.banking.domain.usecases.cards.RemoveCardUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val getCardByNumberUseCase: GetCardByNumberUseCase,
    private val deleteCardByNumberUseCase: RemoveCardUseCase
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

            is CardDetailsIntent.ToggleDeleteCardDialog -> {
                reduceDeleteCardDialog(
                    isShown = intent.isDialogShown
                )
            }

            is CardDetailsIntent.ConfirmDeleteCard -> {
                reduceDeleteCard()
            }

            CardDetailsIntent.ConsumeCardDeletedEvent -> {

            }
        }
    }


    private fun reduceScreenLoading() {
        _state.value = CardDetailsState.Loading
    }

    private fun reduceSuccessData(card: PaymentCard) {
        _state.value = CardDetailsState.Success(card = CardUi.mapFromDomain(card))
    }

    private fun reduceDeleteCardDialog(
        isShown: Boolean
    ) {
        val currentState = _state.value

        if (currentState is CardDetailsState.Success) {
            _state.value = currentState.copy(
                showDeleteCardDialog = isShown,
            )
        }
    }

    private fun reduceDeleteCard() {
        val currentState = _state.value

        if (currentState is CardDetailsState.Success) {
            _state.value = currentState.copy(showLoading = true)

            viewModelScope.launch {
                val res = OperationResult.runWrapped {
                    deleteCardByNumberUseCase.execute(currentState.card.cardNumber)
                }

                _state.value = currentState.copy(
                    showLoading = false,
                    cardDeletedResultEvent = triggered(res)
                )
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        _state.value = CardDetailsState.Error(error = error.asUiTextError())
    }

    fun consumeDeleteResultEvent() {
        val currentState = _state.value
        if (currentState is CardDetailsState.Success) {
            _state.value = currentState.copy(
                showLoading = false,
                cardDeletedResultEvent = consumed()
            )
        }
    }
}
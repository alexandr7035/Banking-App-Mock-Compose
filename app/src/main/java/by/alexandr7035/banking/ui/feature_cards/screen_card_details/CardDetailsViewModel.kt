package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.account.GetCardBalanceObservableUseCase
import by.alexandr7035.banking.domain.features.cards.model.PaymentCard
import by.alexandr7035.banking.domain.features.cards.GetCardByIdUseCase
import by.alexandr7035.banking.domain.features.cards.SetCardAsPrimaryUseCase
import by.alexandr7035.banking.domain.features.cards.RemoveCardUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardDetailsViewModel(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val deleteCardByNumberUseCase: RemoveCardUseCase,
    private val getCardBalanceObservableUseCase: GetCardBalanceObservableUseCase,
    private val setCardAsPrimaryUseCase: SetCardAsPrimaryUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CardDetailsState> = MutableStateFlow(CardDetailsState.Loading)
    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        reduceError(ErrorType.fromThrowable(throwable))
    }

    fun emitIntent(intent: CardDetailsIntent) {
        when (intent) {
            is CardDetailsIntent.EnterScreen -> {
                reduceScreenLoading()

                viewModelScope.launch(errorHandler) {
                    val card = getCardByIdUseCase.execute(intent.cardId)
                    val cardBalanceFlow = getCardBalanceObservableUseCase
                        .execute(intent.cardId)
                        .map { MoneyAmountUi.mapFromDomain(it).amountStr }
                        .catch { reduceError(ErrorType.fromThrowable(it)) }

                    reduceSuccessData(card, cardBalanceFlow)
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

            is CardDetailsIntent.SetCardAsPrimary -> {
                val currentState = _state.value

                if (currentState is CardDetailsState.Success) {
                    viewModelScope.launch(errorHandler) {
                        val res = OperationResult.runWrapped {
                            setCardAsPrimaryUseCase.execute(
                                cardId = currentState.card.id,
                                setAsPrimary = intent.makePrimary
                            )
                        }

                        val updatedState = when (res) {
                            is OperationResult.Success -> {
                                    currentState.copy(
                                        setCardAsPrimaryEvent = triggered(res),
                                        card = currentState.card.copy(isPrimary = intent.makePrimary)
                                    )
                            }

                            is OperationResult.Failure -> {
                                    currentState.copy(
                                        setCardAsPrimaryEvent = triggered(res),
                                        card = currentState.card.copy(isPrimary = intent.makePrimary)
                                    )
                            }

                        }

                        _state.update { updatedState }
                    }
                }
            }
        }
    }


    private fun reduceScreenLoading() {
        _state.value = CardDetailsState.Loading
    }

    private fun reduceSuccessData(
        card: PaymentCard,
        balanceFlow: Flow<String>
    ) {
        _state.value = CardDetailsState.Success(
            card = CardUi.mapFromDomain(
                card = card,
                balanceFlow = balanceFlow
            )
        )
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

            viewModelScope.launch(errorHandler) {
                val res = OperationResult.runWrapped {
                    deleteCardByNumberUseCase.execute(currentState.card.id)
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

    fun consumeSetCardAsDefaultEvent() {
        val currentState = _state.value
        if (currentState is CardDetailsState.Success) {
            _state.value = currentState.copy(
                showLoading = false,
                setCardAsPrimaryEvent = consumed()
            )
        }
    }
}
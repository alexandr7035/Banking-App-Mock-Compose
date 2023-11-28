package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.account.GetCardBalanceObservableUseCase
import by.alexandr7035.banking.domain.features.cards.GetAllCardsUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardListViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase, private val getCardBalanceObservableUseCase: GetCardBalanceObservableUseCase
) : ViewModel() {
    private val _state: MutableStateFlow<CardListState> = MutableStateFlow(
        CardListState(isLoading = true)
    )

    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        reduceError(ErrorType.fromThrowable(throwable))
    }

    fun emitIntent(intent: CardListIntent) {
        when (intent) {
            is CardListIntent.EnterScreen -> load()
            is CardListIntent.ToggleFloatingAddCardButton -> {
                _state.update {
                    it.copy(floatingAddCardShown = intent.isShown)
                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch(errorHandler) {
            reduceLoading()
            val cards = getAllCardsUseCase.execute()
            val cardsUi = cards.map { card ->
                CardUi.mapFromDomain(
                    card = card,
                    balanceFlow = getCardBalanceObservableUseCase.execute(card.cardId).map {
                        MoneyAmountUi.mapFromDomain(it).amountStr
                    }.catch { err ->
                        val error = ErrorType.fromThrowable(err)
                        if (error != ErrorType.CARD_HAS_BEEN_DELETED) {
                            reduceError(error)
                        }
                    }
                )
            }

            reduceData(cardsUi)
        }
    }

    private fun reduceLoading() {
        _state.update {
            it.copy(isLoading = true)
        }
    }

    private fun reduceError(errorType: ErrorType) {
        _state.update {
            it.copy(
                isLoading = false,
                error = errorType.asUiTextError()
            )
        }
    }

    private fun reduceData(cards: List<CardUi>) {
        _state.update {
            it.copy(
                isLoading = false,
                cards = cards
            )
        }
    }
}
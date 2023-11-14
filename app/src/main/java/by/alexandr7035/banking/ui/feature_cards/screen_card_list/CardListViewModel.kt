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
        CardListState.Loading
    )

    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        reduceError(ErrorType.fromThrowable(throwable))
    }

    fun emitIntent(intent: CardListIntent) {
        when (intent) {
            is CardListIntent.EnterScreen -> load()
            is CardListIntent.ToggleFloatingAddCardButton -> {
                val currState = _state.value
                if (currState is CardListState.Success) {
                    _state.value = currState.copy(floatingAddCardShown = intent.isShown)
                }
            }
        }
    }

    private fun load() {
        viewModelScope.launch(errorHandler) {
            // TODO fix array crash without loading
            reduceLoading()

            val cards = getAllCardsUseCase.execute()
            val cardsUi = cards.map {card ->
                CardUi.mapFromDomain(
                    card = card,
                    balanceFlow = getCardBalanceObservableUseCase.execute(card.cardId).map {
                        MoneyAmountUi.mapFromDomain(it).amountStr
                    }.catch { err ->
                        reduceError(ErrorType.fromThrowable(err))
                    }
                )
            }

            reduceData(cardsUi)
        }
    }

    private fun reduceLoading() {
        _state.update {
            CardListState.Loading
        }
    }

    private fun reduceError(errorType: ErrorType) {
        _state.update {
            CardListState.Error(errorType.asUiTextError())
        }
    }

    private fun reduceData(cards: List<CardUi>) {
        _state.update {
            CardListState.Success(cards)
        }
    }
}
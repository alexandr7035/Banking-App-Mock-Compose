package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.cards.GetAllCardsUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardListViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase
): ViewModel() {
    private val _state: MutableStateFlow<CardListState> = MutableStateFlow(
        CardListState.Loading
    )

    val state = _state.asStateFlow()

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
        viewModelScope.launch {
            reduceLoading()

            val res = OperationResult.runWrapped {
                getAllCardsUseCase.execute()
            }

            when (res) {
                is OperationResult.Success -> {
                    val cardsUi = res.data.map {
                        CardUi.mapFromDomain(it)
                    }

                    reduceData(cardsUi)
                }

                is OperationResult.Failure -> {
                    reduceError(res.error.errorType)
                }
            }

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
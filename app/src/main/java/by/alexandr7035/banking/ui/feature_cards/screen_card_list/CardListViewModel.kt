package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.ui.components.error.UiError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardListViewModel(): ViewModel() {
    private val _state: MutableStateFlow<CardListState> = MutableStateFlow(
        CardListState.Loading
    )

    val state = _state.asStateFlow()

    fun emitIntent(intent: CardListIntent) {
        when (intent) {
            is CardListIntent.EnterScreen -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            reduceLoading()

            // TODO repository
            delay(500)
            val cards = List(2) {
                CardUi.mock()
            }

            reduceData(cards)
        }
    }

    private fun reduceLoading() {
        _state.update {
            CardListState.Loading
        }
    }

    private fun reduceError(uiError: UiError) {
        _state.update {
            CardListState.Error(uiError)
        }
    }

    private fun reduceData(cards: List<CardUi>) {
        _state.update {
            CardListState.Success(cards)
        }
    }
}
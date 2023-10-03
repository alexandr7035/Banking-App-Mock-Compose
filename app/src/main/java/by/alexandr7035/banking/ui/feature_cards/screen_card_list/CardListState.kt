package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import by.alexandr7035.banking.ui.components.error.UiError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi

sealed class CardListState {
    data class Success(val cards: List<CardUi>) : CardListState()
    data class Error(val error: UiError) : CardListState()
    object Loading: CardListState()
}

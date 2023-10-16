package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.model.CardUi

sealed class CardListState {
    data class Success(
        val cards: List<CardUi>,
        val floatingAddCardShown: Boolean = false
    ) : CardListState()

    data class Error(val error: UiText) : CardListState()
    object Loading : CardListState()
}

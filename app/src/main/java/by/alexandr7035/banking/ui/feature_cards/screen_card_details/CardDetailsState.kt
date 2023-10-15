package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.model.CardUi

sealed class CardDetailsState {
    data class Success(
        val card: CardUi,
        val showLoading: Boolean = false,
        val showDeleteCardDialog: Boolean = false
    ) : CardDetailsState()

    data class Error(val error: UiText) : CardDetailsState()

    object Loading : CardDetailsState()
}

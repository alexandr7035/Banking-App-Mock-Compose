package by.alexandr7035.banking.ui.feature_cards.dialog_card_picker

import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.model.CardUi

data class CardPickerState(
    val isLoading: Boolean = false,
    val cards: List<CardUi>? = null,
    val error: UiText? = null
)

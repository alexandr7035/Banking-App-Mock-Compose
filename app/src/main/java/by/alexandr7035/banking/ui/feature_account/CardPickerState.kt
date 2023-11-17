package by.alexandr7035.banking.ui.feature_account

import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class CardPickerState(
    val isLoading: Boolean = false,
    val showCardPicker: Boolean = false,
    val selectedCard: CardUi? = null,
    val cardSelectErrorEvent: StateEventWithContent<ErrorType> = consumed()
)
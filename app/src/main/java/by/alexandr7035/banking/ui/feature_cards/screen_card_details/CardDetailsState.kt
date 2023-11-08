package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

sealed class CardDetailsState {
    data class Success(
        val card: CardUi,
        val showLoading: Boolean = false,
        val showDeleteCardDialog: Boolean = false,
        val cardDeletedResultEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
        val setCardAsPrimaryEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
    ) : CardDetailsState()

    data class Error(val error: UiText) : CardDetailsState()

    object Loading : CardDetailsState()
}

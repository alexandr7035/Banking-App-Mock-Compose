package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class CardDetailsState(
    val card: CardUi? = null,
    val showCardSkeleton: Boolean = true,
    val error: UiText? = null,
    val showLoading: Boolean = false,
    val showDeleteCardDialog: Boolean = false,
    val cardDeletedResultEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
    val setCardAsPrimaryEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
)

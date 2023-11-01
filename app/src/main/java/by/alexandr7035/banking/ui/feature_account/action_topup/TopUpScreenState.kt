package by.alexandr7035.banking.ui.feature_account.action_topup

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class TopUpScreenState(
//    val selectedCard: CardUi? = null,
    val selectedCard: CardUi? = CardUi.mock(),
//    val selectedContact: ContactUi? = null,
    val selectedAmount: MoneyAmount = MoneyAmount(50F),
    val proposedValues: Set<MoneyAmount> = emptySet(),
    val topUpEvent: StateEvent = consumed,
    val proceedButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)

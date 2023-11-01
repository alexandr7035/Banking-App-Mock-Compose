package by.alexandr7035.banking.ui.feature_account.action_topup

import by.alexandr7035.banking.domain.features.account.model.BalanceValue
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class TopUpScreenState(
//    val selectedCard: CardUi? = null,
    val selectedCard: CardUi? = CardUi.mock(),
//    val selectedContact: ContactUi? = null,
    val selectedAmount: BalanceValue = BalanceValue.LongBalance(50),
    val proposedValues: Set<BalanceValue> = emptySet(),
    val topUpEvent: StateEvent = consumed,
)

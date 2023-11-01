package by.alexandr7035.banking.ui.feature_account.action_topup

import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class TopUpScreenState(
    val cardPickerState: CardPickerState = CardPickerState(),
    val amountState: AmountPickersState = AmountPickersState(),
    val isLoading: Boolean = false,
    val topUpSubmittedEvent: StateEvent = consumed,
) {
    data class CardPickerState(
        val isLoading: Boolean = false,
        val showCardPicker: Boolean = false,
        val selectedCard: CardUi? = null,
        val cardSelectErrorEvent: StateEventWithContent<ErrorType> = consumed()
    )

    data class AmountPickersState(
        val selectedAmount: MoneyAmount = MoneyAmount(0F),
        val proposedValues: Set<MoneyAmount> = emptySet(),
    )

    val proceedButtonEnabled
            get(): Boolean {
                return amountState.selectedAmount != MoneyAmount(0f) && cardPickerState.selectedCard != null
            }
}

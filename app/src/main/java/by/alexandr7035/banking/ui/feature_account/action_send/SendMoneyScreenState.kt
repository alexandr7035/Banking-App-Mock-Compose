package by.alexandr7035.banking.ui.feature_account.action_send

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_account.AmountPickersState
import by.alexandr7035.banking.ui.feature_account.CardPickerState
import by.alexandr7035.banking.ui.feature_account.ContactPickerState
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class SendMoneyScreenState(
    val cardPickerState: CardPickerState = CardPickerState(),
    val amountState: AmountPickersState = AmountPickersState(),
    val contactPickerState: ContactPickerState = ContactPickerState(),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val sendSubmittedEvent: StateEvent = consumed,
    val showSuccessDialog: Boolean = false
) {
    val proceedButtonEnabled
        get() = amountState.selectedAmount != MoneyAmount(0f)
                    && cardPickerState.selectedCard != null
                    && contactPickerState.selectedContact != null

    val amountPickersEnabled
        get() = cardPickerState.selectedCard != null
}

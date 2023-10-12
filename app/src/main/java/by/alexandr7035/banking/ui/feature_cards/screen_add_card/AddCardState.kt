package by.alexandr7035.banking.ui.feature_cards.screen_add_card

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class AddCardState(
    val cardFields: AddCardFormFields = AddCardFormFields(),
    val isLoading: Boolean = false,
    val showDatePicker: Boolean = false,
    val cardSavedEvent: StateEventWithContent<Boolean> = consumed()
) {
    companion object {
        fun mock(
            isLoading: Boolean = false,
            showDatePicker: Boolean = false
        ) = AddCardState(
            cardFields = AddCardFormFields(
                cardNumber = "2298126833989874",
                cardHolder = "Alexander Michael",
                addressFirstLine = "2890 Pangandaran Street",
                addressSecondLine = "",
                cvvCode = "123",
            ),
            isLoading = isLoading,
            showDatePicker = showDatePicker
        )
    }
}

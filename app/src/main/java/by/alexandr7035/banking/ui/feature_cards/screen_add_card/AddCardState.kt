package by.alexandr7035.banking.ui.feature_cards.screen_add_card

import by.alexandr7035.banking.domain.core.OperationResult
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class AddCardState(
    val formFields: AddCardFormFields = AddCardFormFields(),
    val isLoading: Boolean = false,
    val showDatePicker: Boolean = false,
    val cardSavedEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
) {
    companion object {
        fun mock(
            isLoading: Boolean = false,
            showDatePicker: Boolean = false
        ) = AddCardState(
            formFields = AddCardFormFields(
                cardNumber = UiField("2298126833989874"),
                cardHolder = UiField("Alexander Michael"),
                addressFirstLine = UiField("2890 Pangandaran Street"),
                addressSecondLine = UiField(""),
                cvvCode = UiField("123"),
                expirationDateTimestamp = null,
                expirationDate = UiField("-")
            ),
            isLoading = isLoading,
            showDatePicker = showDatePicker
        )
    }
}

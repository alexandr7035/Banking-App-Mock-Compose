package by.alexandr7035.banking.ui.feature_cards.screen_add_card

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.core.extensions.getFormattedDate
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
        ): AddCardState {
            // + 365 days
            val mockExpiration = System.currentTimeMillis() + 31556926000L

            return  AddCardState(
                formFields = AddCardFormFields(
                    cardNumber = UiField("2298126833989874"),
                    cardHolder = UiField("Alexander Michael"),
                    addressFirstLine = UiField("2890 Pangandaran Street"),
                    addressSecondLine = UiField(""),
                    cvvCode = UiField("123"),
                    expirationDateTimestamp =  mockExpiration,
                    expirationDate = UiField(mockExpiration.getFormattedDate("dd MMM yy"))
                ),
                isLoading = isLoading,
                showDatePicker = showDatePicker
            )
        }
    }
}

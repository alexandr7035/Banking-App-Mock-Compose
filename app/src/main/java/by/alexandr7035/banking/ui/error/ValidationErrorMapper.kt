package by.alexandr7035.banking.ui.error

import by.alexandr7035.banking.domain.core.ErrorType

class ValidationErrorMapper {
    fun mapToUi(validationError: ErrorType): String {
        // TODO UiText class
        return when (validationError) {
            ErrorType.FIELD_IS_EMPTY -> "This field cannot be empty"
            ErrorType.INVALID_CARD_NUMBER -> "Invalid card number"
            ErrorType.CARD_EXPIRED -> "Card has expired"
            ErrorType.INVALID_CVV ->  "CVV should have 3 or 4 digits"
            ErrorType.INVALID_CARDHOLDER -> "Invalid holder name"
            ErrorType.DATE_UNSPECIFIED -> "Please, specify date"
            else -> "Check correctness of the input"
        }
    }
}
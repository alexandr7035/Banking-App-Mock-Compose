package by.alexandr7035.banking.ui.error

import by.alexandr7035.banking.domain.usecases.validation.ValidationError

class ValidationErrorMapper {
    fun mapToUi(validationError: ValidationError): String {
        // TODO UiText class
        return when (validationError) {
            ValidationError.FIELD_IS_EMPTY -> "This field cannot be empty"
            ValidationError.INVALID_CARD_NUMBER -> "Invalid card number"
            ValidationError.CARD_EXPIRED -> "Card has expired"
            ValidationError.INVALID_CVV ->  "CVV should have 3 or 4 digits"
            ValidationError.INVALID_CARDHOLDER -> "Invalid holder name"
            ValidationError.BILLING_ADDRESS_SHOULD_CONTAIN_ONE_LINE -> "TODO"
        }
    }
}
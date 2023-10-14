package by.alexandr7035.banking.ui.core.error

import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.ui.core.resources.UiText

fun ErrorType.asUiTextError(): UiText {
    return when (this) {
        ErrorType.FIELD_IS_EMPTY -> UiText.StringResource(R.string.field_is_empty)
        ErrorType.INVALID_CARD_NUMBER -> UiText.StringResource(R.string.invalid_card_number)
        ErrorType.CARD_EXPIRED ->  UiText.StringResource(R.string.card_has_expired)
        ErrorType.INVALID_CVV ->  UiText.StringResource(R.string.invalid_cvv)
        ErrorType.DATE_UNSPECIFIED -> UiText.StringResource(R.string.date_not_specified)
        ErrorType.CARD_NOT_FOUND ->  UiText.StringResource(R.string.card_not_found)
        ErrorType.CARD_ALREADY_ADDED ->  UiText.StringResource(R.string.card_already_added)
        ErrorType.GENERIC_VALIDATION_ERROR ->  UiText.StringResource(R.string.generic_validation_error)

        ErrorType.UNKNOWN_ERROR ->  UiText.StringResource(R.string.invalid_card_number)
    }
}
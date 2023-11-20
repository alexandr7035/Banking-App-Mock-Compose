package by.alexandr7035.banking.ui.core.error

import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.ui.core.resources.UiText

fun ErrorType.asUiTextError(): UiText {
    return when (this) {
        ErrorType.USER_NOT_FOUND -> UiText.StringResource(R.string.user_not_found)
        ErrorType.WRONG_PASSWORD -> UiText.StringResource(R.string.wrong_password)

        ErrorType.FIELD_IS_EMPTY -> UiText.StringResource(R.string.field_is_empty)
        ErrorType.GENERIC_VALIDATION_ERROR ->  UiText.StringResource(R.string.generic_validation_error)

        ErrorType.INVALID_CARD_NUMBER -> UiText.StringResource(R.string.invalid_card_number)
        ErrorType.CARD_EXPIRED ->  UiText.StringResource(R.string.card_has_expired)
        ErrorType.INVALID_CVV ->  UiText.StringResource(R.string.invalid_cvv)
        ErrorType.DATE_UNSPECIFIED -> UiText.StringResource(R.string.date_not_specified)
        ErrorType.CARD_NOT_FOUND ->  UiText.StringResource(R.string.card_not_found)
        ErrorType.CARD_HAS_BEEN_DELETED -> UiText.DynamicString("Card has been deleted")
        ErrorType.CARD_ALREADY_ADDED ->  UiText.StringResource(R.string.card_already_added)
        ErrorType.INSUFFICIENT_CARD_BALANCE -> UiText.StringResource(R.string.insufficient_card_balance)

        ErrorType.TRANSACTION_NOT_FOUND -> UiText.StringResource(R.string.transaction_not_found)

        ErrorType.INVALID_PASSWORD_FIELD -> UiText.StringResource(R.string.invalid_password_field)
        ErrorType.INVALID_EMAIL_FIELD ->  UiText.StringResource(R.string.invalid_email_field)

        ErrorType.UNKNOWN_ERROR ->  UiText.StringResource(R.string.unknown_error)

        ErrorType.GENERIC_NOT_FOUND_ERROR -> UiText.StringResource(R.string.generic_not_found_error)
        ErrorType.WRONG_CONFIRMATION_CODE -> UiText.StringResource(R.string.wrong_confirmation_code)
    }
}
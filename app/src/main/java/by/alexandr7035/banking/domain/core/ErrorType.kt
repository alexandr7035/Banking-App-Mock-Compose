package by.alexandr7035.banking.domain.core

enum class ErrorType {
    USER_NOT_FOUND,
    WRONG_PASSWORD,
    WRONG_CONFIRMATION_CODE,
    FIELD_IS_EMPTY,
    INVALID_CARD_NUMBER,
    CARD_EXPIRED,
    INVALID_CVV,
    DATE_UNSPECIFIED,
    CARD_NOT_FOUND,
    CARD_ALREADY_ADDED,
    INVALID_PASSWORD_FIELD,
    INVALID_EMAIL_FIELD,
    TRANSACTION_NOT_FOUND,

    GENERIC_VALIDATION_ERROR,
    GENERIC_NOT_FOUND_ERROR,

    UNKNOWN_ERROR,;

    companion object {
        fun fromThrowable(e: Throwable): ErrorType {
            return when (e) {
                is AppError -> e.errorType
                else -> UNKNOWN_ERROR
            }
        }
    }
}
package by.alexandr7035.banking.domain.core

enum class ErrorType {
    FIELD_IS_EMPTY,
    INVALID_CARD_NUMBER,
    CARD_EXPIRED,
    INVALID_CVV,
    INVALID_CARDHOLDER,
    DATE_UNSPECIFIED,
    CARD_NOT_FOUND,
    CARD_ALREADY_ADDED,
    UNKNOWN_ERROR,
}
package by.alexandr7035.banking.domain.usecases.validation

enum class ValidationError {
    FIELD_IS_EMPTY,
    INVALID_CARD_NUMBER,
    CARD_EXPIRED,
    INVALID_CVV,
    INVALID_CARDHOLDER,
    DATE_UNSPECIFIED
}
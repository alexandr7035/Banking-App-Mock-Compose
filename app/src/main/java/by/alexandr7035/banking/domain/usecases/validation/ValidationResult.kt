package by.alexandr7035.banking.domain.usecases.validation

import by.alexandr7035.banking.domain.core.ErrorType

data class ValidationResult(
    val isValid: Boolean,
    val validationError: ErrorType? = null
)

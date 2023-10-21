package by.alexandr7035.banking.domain.features.validation.model

import by.alexandr7035.banking.domain.core.ErrorType

data class ValidationResult(
    val isValid: Boolean,
    val validationError: ErrorType? = null
)

package by.alexandr7035.banking.domain.usecases.validation

data class ValidationResult(
    val isValid: Boolean,
    val validationError: ValidationError? = null
)

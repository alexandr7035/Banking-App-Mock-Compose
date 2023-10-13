package by.alexandr7035.banking.domain.usecases.validation

class ValidateCvvCodeUseCase {
    fun execute(cvv: String): ValidationResult {
        return if (cvv.isBlank()) {
            ValidationResult(isValid = false, validationError = ValidationError.FIELD_IS_EMPTY)
        } else {
            // Regular expression for 3 or 4 digits
            val cvvPattern = "\\d{3,4}".toRegex()

            if (cvvPattern.matches(cvv)) {
                ValidationResult(isValid = true, validationError = null)
            } else {
                ValidationResult(isValid = false, validationError = ValidationError.INVALID_CVV)
            }
        }
    }
}
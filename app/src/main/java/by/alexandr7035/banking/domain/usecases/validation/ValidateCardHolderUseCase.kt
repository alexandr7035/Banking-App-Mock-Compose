package by.alexandr7035.banking.domain.usecases.validation

class ValidateCardHolderUseCase {
    fun execute(cardHolder: String): ValidationResult {
        return if (cardHolder.isNotBlank()) {
            ValidationResult(isValid = true)
        } else {
            ValidationResult(isValid = false, validationError = ValidationError.FIELD_IS_EMPTY)
        }
    }
}
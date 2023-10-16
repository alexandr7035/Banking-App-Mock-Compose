package by.alexandr7035.banking.domain.usecases.validation

import by.alexandr7035.banking.domain.core.ErrorType

class ValidateCardHolderUseCase {
    fun execute(cardHolder: String): ValidationResult {
        return if (cardHolder.isNotBlank()) {
            ValidationResult(isValid = true)
        } else {
            ValidationResult(isValid = false, validationError = ErrorType.FIELD_IS_EMPTY)
        }
    }
}
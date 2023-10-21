package by.alexandr7035.banking.domain.features.validation

import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.validation.model.ValidationResult

class ValidateCardHolderUseCase {
    fun execute(cardHolder: String): ValidationResult {
        return if (cardHolder.isNotBlank()) {
            ValidationResult(isValid = true)
        } else {
            ValidationResult(isValid = false, validationError = ErrorType.FIELD_IS_EMPTY)
        }
    }
}
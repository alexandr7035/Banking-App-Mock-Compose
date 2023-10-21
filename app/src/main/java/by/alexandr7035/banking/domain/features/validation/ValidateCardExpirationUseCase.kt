package by.alexandr7035.banking.domain.features.validation

import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.validation.model.ValidationResult

class ValidateCardExpirationUseCase {
    fun execute(expirationTime: Long?): ValidationResult {
        return if (expirationTime == null) {
            ValidationResult(isValid = false, validationError = ErrorType.DATE_UNSPECIFIED)
        } else if (System.currentTimeMillis() > expirationTime) {
            ValidationResult(isValid = false, validationError = ErrorType.CARD_EXPIRED)
        } else {
            ValidationResult(isValid = true, validationError = null)
        }
    }
}
package by.alexandr7035.banking.domain.features.validation

import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.validation.model.ValidationResult

class ValidateBillingAddressUseCase {
    fun execute(
        addressFirstLine: String,
        addressSecondLine: String
    ): ValidationResult {
        // Check only first line
        return if (addressFirstLine.isBlank()) {
            ValidationResult(true, validationError = ErrorType.FIELD_IS_EMPTY)
        } else {
            ValidationResult(isValid = true, validationError = null)
        }
    }
}
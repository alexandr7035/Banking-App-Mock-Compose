package by.alexandr7035.banking.domain.usecases.validation

import by.alexandr7035.banking.domain.core.ErrorType

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
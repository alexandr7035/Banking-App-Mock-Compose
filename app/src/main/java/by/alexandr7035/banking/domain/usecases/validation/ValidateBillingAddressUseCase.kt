package by.alexandr7035.banking.domain.usecases.validation

class ValidateBillingAddressUseCase {
    fun execute(
        addressFirstLine: String,
        addressSecondLine: String
    ): ValidationResult {
        // Check only first line
        return if (addressFirstLine.isBlank()) {
            ValidationResult(true, validationError = ValidationError.FIELD_IS_EMPTY)
        } else {
            ValidationResult(isValid = true, validationError = null)
        }
    }
}
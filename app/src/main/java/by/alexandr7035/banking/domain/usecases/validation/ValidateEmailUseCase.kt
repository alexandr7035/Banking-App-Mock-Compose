package by.alexandr7035.banking.domain.usecases.validation

import android.text.TextUtils
import android.util.Patterns
import by.alexandr7035.banking.domain.core.ErrorType

class ValidateEmailUseCase {
    fun execute(email: String): ValidationResult {
        return if (email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ValidationResult(isValid = true, validationError = null)
        } else if (email.isBlank()) {
            ValidationResult(isValid = false, validationError = ErrorType.FIELD_IS_EMPTY)
        } else {
            ValidationResult(isValid = false, validationError = ErrorType.INVALID_EMAIL_FIELD)
        }
    }
}
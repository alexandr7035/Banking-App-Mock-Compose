package by.alexandr7035.banking.ui.components.error

import by.alexandr7035.banking.domain.core.ErrorType

data class UiError(
    val title: String,
    val message: String
) {
    companion object {
        fun fromDomainError(type: ErrorType): UiError {
            return when (type) {
                // TODO error mappings
                else -> UiError("An error occurred", "Details: ${type.name}")
            }
        }
    }
}

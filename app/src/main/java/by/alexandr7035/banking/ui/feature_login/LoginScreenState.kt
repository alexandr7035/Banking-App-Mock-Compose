package by.alexandr7035.banking.ui.feature_login

import by.alexandr7035.banking.ui.validation.FieldValidationResult
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class LoginScreenState(
    val loginField: FieldValidationResult = FieldValidationResult(),
    val passwordField: FieldValidationResult = FieldValidationResult(),
    val isLoading: Boolean = false,
    val onLoginEvent: StateEventWithContent<LoginResult> = consumed()
)

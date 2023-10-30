package by.alexandr7035.banking.ui.feature_login

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class LoginScreenState(
    val formFields: LoginFormFields = LoginFormFields(),
    val isLoading: Boolean = false,
    val loginEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
) {
    companion object {
        fun mock() = LoginScreenState(
            formFields = LoginFormFields(
                loginField = UiField("example@mail.com"),
                passwordField = UiField("1234567Ab")
            )
        )
    }
}

data class LoginFormFields(
    val loginField: UiField = UiField("", null),
    val passwordField: UiField = UiField("", null),
)

enum class LoginFieldType() {
    EMAIL,
    PASSWORD
}

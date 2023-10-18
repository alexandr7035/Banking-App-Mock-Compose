package by.alexandr7035.banking.ui.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.usecases.login.LoginWithEmailUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateEmailUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidatePasswordUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginWithEmailUseCase: LoginWithEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginScreenState())
    val loginState = _loginState.asStateFlow()

    fun emitIntent(intent: LoginIntent) {
        val currentState = _loginState.value

        when (intent) {
            is LoginIntent.EnterScreen -> {}

            is LoginIntent.LoginFieldChanged -> {
                when (intent.fieldType) {
                    LoginFieldType.EMAIL -> reduceFields(currentState.formFields.copy(loginField = UiField(intent.fieldValue)))
                    LoginFieldType.PASSWORD -> reduceFields(currentState.formFields.copy(passwordField = UiField(intent.fieldValue)))
                }
            }

            // TODO validation use cases
            is LoginIntent.SubmitForm -> {
                val email = currentState.formFields.loginField.value
                val password = currentState.formFields.passwordField.value

                var formValidFlag = true

                val mailValidation = validateEmailUseCase.execute(email)
                val passwordValidation = validatePasswordUseCase.execute(password)

                if (!mailValidation.isValid) {
                    reduceFieldError(LoginFieldType.EMAIL, mailValidation.validationError)
                    formValidFlag = false
                }

                if (!passwordValidation.isValid) {
                    reduceFieldError(LoginFieldType.PASSWORD, passwordValidation.validationError)
                    formValidFlag = false
                }

                if (!formValidFlag) {
                    reduceError(ErrorType.GENERIC_VALIDATION_ERROR)
                } else {
                    reduceTryLogin(login = email, password = password)
                }
            }
        }
    }

    private fun reduceFields(fields: LoginFormFields) {
        _loginState.update { current ->
            current.copy(formFields = fields)
        }
    }

    private fun reduceFieldError(
        fieldType: LoginFieldType,
        errorType: ErrorType?
    ) {
        val currentFields = _loginState.value.formFields

        if (errorType != null) {
            val updatedFields = when (fieldType) {
                LoginFieldType.EMAIL -> {
                    currentFields.copy(loginField = currentFields.loginField.copy(error = errorType.asUiTextError()))
                }

                LoginFieldType.PASSWORD -> {
                    currentFields.copy(passwordField = currentFields.passwordField.copy(error = errorType.asUiTextError()))
                }
            }

            _loginState.update { currentState ->
                currentState.copy(formFields = updatedFields)
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        _loginState.update { curr ->
            curr.copy(
                isLoading = false,
                loginEvent = triggered(
                    OperationResult.Failure(error = AppError(error))
                )
            )
        }
    }

    private fun reduceTryLogin(login: String, password: String) {
        _loginState.update {
            it.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            val loginResult = OperationResult.runWrapped {
                loginWithEmailUseCase.execute(login, password)
            }

            _loginState.update {
                it.copy(
                    isLoading = false,
                    loginEvent = triggered(loginResult)
                )
            }
        }
    }

    fun onLoginEventConsumed() {
        _loginState.update {
            it.copy(
                loginEvent = consumed()
            )
        }
    }
}
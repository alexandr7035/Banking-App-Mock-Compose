package by.alexandr7035.banking.ui.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.data.login.LoginRepository
import by.alexandr7035.banking.ui.validation.FieldValidationResult
import by.alexandr7035.banking.ui.validation.FormValidators
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginScreenState())
    val loginState = _loginState.asStateFlow()

    fun login(login: String, password: String) {
        _loginState.update {
            it.copy(
                isLoading = true,
                passwordField = FieldValidationResult(),
                loginField = FieldValidationResult()
            )
        }

        if (!FormValidators.isEmailValid(login)) {
            _loginState.update {
                it.copy(
                    isLoading = false,
                    loginField = FieldValidationResult(
                        "Username must be a valid e-mail address"
                    )
                )
            }

            return
        }

        if (!FormValidators.isPasswordValid(password)) {
            _loginState.update {
                it.copy(
                    isLoading = false,
                    passwordField = FieldValidationResult(
                        "Wrong password format. At least 8 charters, both latin symbols and numbers"
                    )
                )
            }

            return
        }

        viewModelScope.launch(Dispatchers.IO) {
            val loginResult = loginRepository.login(login, password)

            if (loginResult.isSuccess) {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        loginField = FieldValidationResult(),
                        passwordField = FieldValidationResult(),
                        onLoginEvent = triggered(LoginResult.Success)
                    )
                }
            } else {
                _loginState.update {
                    it.copy(
                        isLoading = false,
                        loginField = FieldValidationResult(),
                        passwordField = FieldValidationResult(),
                        onLoginEvent = triggered(LoginResult.Error(errorUi = loginResult.exceptionOrNull()!!.message!!))
                    )
                }
            }
        }
    }


    fun clearFormValidationErrors() {
        _loginState.update {
            it.copy(
                loginField = FieldValidationResult(),
                passwordField = FieldValidationResult()
            )
        }
    }

    fun onLoginEventTriggered() {
        _loginState.update {
            it.copy(
                onLoginEvent = consumed()
            )
        }
    }
}
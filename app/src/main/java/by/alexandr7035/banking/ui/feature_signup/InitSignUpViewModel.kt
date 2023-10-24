package by.alexandr7035.banking.ui.feature_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.signup.SignUpPayload
import by.alexandr7035.banking.domain.features.signup.SignUpWithEmailUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateEmailUseCase
import by.alexandr7035.banking.domain.features.validation.ValidatePasswordUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InitSignUpViewModel(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
): ViewModel() {
    private val _state = MutableStateFlow(InitSignUpState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: InitSIgnUpIntent) {
        when (intent) {
            is InitSIgnUpIntent.EnterScreen -> {}

            is InitSIgnUpIntent.ToggleTermsAgreed -> {
                reduce(intent)
            }

            is InitSIgnUpIntent.FieldChanged -> {
                reduce(intent)
            }

            is InitSIgnUpIntent.SignUpConfirm -> {
                reduce(intent)
            }
        }
    }

    private fun reduce(intent: InitSIgnUpIntent.ToggleTermsAgreed) {
        _state.update {
            it.copy(
                agreedTerms = intent.agreed,
                signUpBtnEnabled = intent.agreed
            )
        }
    }

    private fun reduce(intent: InitSIgnUpIntent.FieldChanged) {
        val currentFields = _state.value.fields

        val updatedFields = when (intent.fieldType) {
            InitSignUpFieldType.EMAIL -> {
                currentFields.copy(email = UiField(intent.fieldValue))
            }
            InitSignUpFieldType.PASSWORD -> {
                currentFields.copy(password = UiField(intent.fieldValue))
            }
            InitSignUpFieldType.FULL_NAME -> {
                currentFields.copy(fullName = UiField(intent.fieldValue))
            }
        }

        _state.update {
            it.copy(fields = updatedFields)
        }
    }

    private fun reduce(intent: InitSIgnUpIntent.SignUpConfirm) {
        _state.update {
            it.copy(isLoading = true)
        }

        viewModelScope.launch {
            val currentState = _state.value

            val email = currentState.fields.email.value
            val password = currentState.fields.password.value
            val fullName = currentState.fields.fullName.value

            var formValidFlag = true

            val mailValidation = validateEmailUseCase.execute(email)
            val passwordValidation = validatePasswordUseCase.execute(password)

            if (!mailValidation.isValid) {
                reduceFieldError(InitSignUpFieldType.EMAIL, mailValidation.validationError)
                formValidFlag = false
            }

            if (!passwordValidation.isValid) {
                reduceFieldError(InitSignUpFieldType.PASSWORD, passwordValidation.validationError)
                formValidFlag = false
            }

            // TODO use case
            if (fullName.isBlank()) {
                reduceFieldError(InitSignUpFieldType.FULL_NAME, passwordValidation.validationError)
                formValidFlag = false
            }

            val currState = _state.value

            val payload = SignUpPayload(
                email = currState.fields.email.value,
                password = currState.fields.password.value,
                fullName = currState.fields.fullName.value
            )

            if (!formValidFlag) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        initSignUpEvent = triggered(OperationResult.Failure(
                            error = AppError(ErrorType.GENERIC_VALIDATION_ERROR)
                        )),
                    )
                }
            } else {
                val res = OperationResult.runWrapped {
                    signUpWithEmailUseCase.execute(payload)
                }

                _state.update {
                    it.copy(
                        isLoading = false,
                        initSignUpEvent = triggered(res),
                    )
                }
            }
        }
    }

    private fun reduceFieldError(
        fieldType: InitSignUpFieldType,
        errorType: ErrorType?
    ) {
        val currentFields = _state.value.fields

        if (errorType != null) {
            val uiError = errorType.asUiTextError()

            val updatedFields = when (fieldType) {
                InitSignUpFieldType.EMAIL -> {
                    currentFields.copy(email = currentFields.email.copy(error = uiError))

                }
                InitSignUpFieldType.PASSWORD -> {
                    currentFields.copy(password  = currentFields.password.copy(error = uiError))

                }
                InitSignUpFieldType.FULL_NAME -> {
                    currentFields.copy(fullName  = currentFields.fullName.copy(error = uiError))
                }
            }

            _state.update { currentState ->
                currentState.copy(fields = updatedFields)
            }
        }
    }


    fun consumeInitSignUpEvent() {
        _state.update {
            it.copy(
                initSignUpEvent = consumed()
            )
        }
    }
}

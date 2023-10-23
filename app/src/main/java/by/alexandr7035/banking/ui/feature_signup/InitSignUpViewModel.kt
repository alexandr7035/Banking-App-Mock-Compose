package by.alexandr7035.banking.ui.feature_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.signup.SignUpPayload
import by.alexandr7035.banking.domain.features.signup.SignUpWithEmailUseCase
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InitSignUpViewModel(
    private val signUpWithEmailUseCase: SignUpWithEmailUseCase
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
            val currState = _state.value

            val payload = SignUpPayload(
                email = currState.fields.email.value,
                password = currState.fields.password.value,
                fullName = currState.fields.fullName.value
            )

            val res = OperationResult.runWrapped {
                signUpWithEmailUseCase.execute(payload)
            }

            _state.update {
                it.copy(
                    initSignUpEvent = triggered(res)
                )
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

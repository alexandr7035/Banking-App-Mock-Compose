package by.alexandr7035.banking.ui.feature_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class InitSignUpViewModel: ViewModel() {
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
            // TODO use case
            delay(3000)

            _state.update {
                it.copy(
                    initSignUpEvent = triggered(OperationResult.Success(Unit))
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

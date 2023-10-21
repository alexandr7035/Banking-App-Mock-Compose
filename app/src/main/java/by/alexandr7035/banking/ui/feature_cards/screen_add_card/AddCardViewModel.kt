package by.alexandr7035.banking.ui.feature_cards.screen_add_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.BuildConfig
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.features.validation.ValidateBillingAddressUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCardExpirationUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCardHolderUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCardNumberUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCvvCodeUseCase
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.cards.model.AddCardPayload
import by.alexandr7035.banking.domain.features.cards.AddCardUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.core.extensions.getFormattedDate
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddCardViewModel(
    private val validateCardNumberUseCase: ValidateCardNumberUseCase,
    private val validateCvvCodeUseCase: ValidateCvvCodeUseCase,
    private val validateCardExpirationUseCase: ValidateCardExpirationUseCase,
    private val validateCardHolderUseCase: ValidateCardHolderUseCase,
    private val validateBillingAddressUseCase: ValidateBillingAddressUseCase,
    private val addCardUseCase: AddCardUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(AddCardState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: AddCardIntent) {
        val currentState = _state.value

        when (intent) {
            is AddCardIntent.EnterScreen -> {
                if (BuildConfig.DEBUG) {
                    reduceInitialMockState()
                }
            }

            is AddCardIntent.StringFieldChanged -> {
                when (intent.fieldType) {
                    AddCardFieldType.CARD_NUMBER -> reduceFields(currentState.formFields.copy(cardNumber = UiField(intent.fieldValue)))
                    AddCardFieldType.CARD_HOLDER -> reduceFields(currentState.formFields.copy(cardHolder = UiField(intent.fieldValue)))
                    AddCardFieldType.ADDRESS_LINE_1 -> reduceFields(currentState.formFields.copy(addressFirstLine = UiField(intent.fieldValue)))
                    AddCardFieldType.ADDRESS_LINE_2 -> reduceFields(currentState.formFields.copy(addressSecondLine = UiField(intent.fieldValue)))
                    AddCardFieldType.CVV_CODE -> reduceFields(currentState.formFields.copy(cvvCode = UiField(intent.fieldValue)))
                    AddCardFieldType.CARD_EXPIRATION_DATE -> { /* handled in other intent */
                    }
                }
            }

            is AddCardIntent.ToggleDatePicker -> {
                _state.update { curr ->
                    curr.copy(showDatePicker = intent.isShown)
                }
            }

            is AddCardIntent.ExpirationPickerSet -> {
                val formatted = if (intent.date == null) {
                    "-"
                } else {
                    intent.date.getFormattedDate("dd MMM yyyy")
                }

                reduceFields(
                    currentState.formFields.copy(
                        expirationDate = UiField(formatted), expirationDateTimestamp = intent.date
                    )
                )
            }

            is AddCardIntent.SaveCard -> {
                _state.update { curr ->
                    curr.copy(
                        isLoading = true
                    )
                }

                viewModelScope.launch {
                    val formValid = reduceValidationWithResult()
                    if (!formValid) {
                        _state.update { curr ->
                            curr.copy(
                                isLoading = false,
                                cardSavedEvent = triggered(
                                    OperationResult.Failure(error = AppError(ErrorType.GENERIC_VALIDATION_ERROR))
                                )
                            )
                        }
                    } else {
                        val res = OperationResult.runWrapped {
                            addCardUseCase.execute(payload = AddCardPayload(
                                cardNumber = currentState.formFields.cardNumber.value,
                                cardHolder = currentState.formFields.cardHolder.value,
                                expirationDate = currentState.formFields.expirationDateTimestamp!!,
                                addressFirstLine = currentState.formFields.addressFirstLine.value,
                                addressSecondLine = currentState.formFields.addressSecondLine.value,
                                cvvCode =   currentState.formFields.cvvCode.value
                            )
                            )
                        }

                        when (res) {
                            is OperationResult.Success -> {
                                _state.update { curr ->
                                    curr.copy(
                                        isLoading = false, cardSavedEvent = triggered(res)
                                    )
                                }
                            }
                            is OperationResult.Failure -> {
                                _state.update { curr ->
                                    curr.copy(
                                        isLoading = false, cardSavedEvent = triggered(res)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            is AddCardIntent.ConsumeResultEvent -> {
                _state.update { curr ->
                    curr.copy(
                        cardSavedEvent = consumed()
                    )
                }
            }
        }
    }

    private fun reduceFields(
        cardFields: AddCardFormFields
    ) {
        _state.update { curr ->
            curr.copy(formFields = cardFields)
        }
    }

    private fun reduceValidationWithResult(): Boolean {
        val currFields = _state.value.formFields

        val validationChain = listOf(
            {
                val res = validateCardNumberUseCase.execute(
                    cardNumber = currFields.cardNumber.value
                )

                reduceErrors(AddCardFieldType.CARD_NUMBER, res.validationError)

                res
            },
            {
                val res = validateCvvCodeUseCase.execute(
                    cvv = currFields.cvvCode.value
                )

                reduceErrors(AddCardFieldType.CVV_CODE, res.validationError)

                res
            },
            {
                val res = validateCardExpirationUseCase.execute(
                    expirationTime = currFields.expirationDateTimestamp
                )

                reduceErrors(AddCardFieldType.CARD_EXPIRATION_DATE, res.validationError)

                res
            },
            {
                val res = validateCardHolderUseCase.execute(
                    cardHolder = currFields.cardHolder.value
                )

                reduceErrors(AddCardFieldType.CARD_HOLDER, res.validationError)

                res
            },
            {
                val res = validateBillingAddressUseCase.execute(
                    addressFirstLine = currFields.addressFirstLine.value,
                    addressSecondLine = currFields.addressSecondLine.value,
                )

                reduceErrors(AddCardFieldType.ADDRESS_LINE_1, res.validationError)

                res
            }
        )

        // This will stop validation on first invalid field
        // return validationChain.all { it.invoke().isValid }

        var formValidFlag = true

        validationChain.forEach {
            val validationRes = it.invoke().isValid
            if (!validationRes) formValidFlag = false
        }

        return formValidFlag
    }

    private fun reduceErrors(
        fieldType: AddCardFieldType, error: ErrorType?
    ) {
        val currentFields = _state.value.formFields

        if (error != null) {
            val uiError = error.asUiTextError()

            val updatedFields = when (fieldType) {
                AddCardFieldType.CARD_NUMBER -> {
                    currentFields.copy(cardNumber = currentFields.cardNumber.copy(error = uiError))
                }

                AddCardFieldType.CARD_HOLDER -> {
                    currentFields.copy(cardHolder = currentFields.cardHolder.copy(error = uiError))
                }

                AddCardFieldType.ADDRESS_LINE_1 -> {
                    currentFields.copy(addressFirstLine = currentFields.addressFirstLine.copy(error = uiError))
                }

                AddCardFieldType.ADDRESS_LINE_2 -> {
                    currentFields.copy(addressSecondLine = currentFields.addressSecondLine.copy(error = uiError))
                }

                AddCardFieldType.CVV_CODE -> {
                    currentFields.copy(cvvCode = currentFields.cvvCode.copy(error = uiError))
                }

                AddCardFieldType.CARD_EXPIRATION_DATE -> {
                    currentFields.copy(expirationDate = currentFields.expirationDate.copy(error = uiError))
                }
            }

            _state.update { curr ->
                curr.copy(formFields = updatedFields)
            }
        }
    }

    private fun reduceInitialMockState() {
        _state.update {
            AddCardState.mock()
        }
    }

    fun consumeSaveCardEvent() {
        this.emitIntent(AddCardIntent.ConsumeResultEvent)
    }
}
package by.alexandr7035.banking.ui.feature_cards.screen_add_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.BuildConfig
import by.alexandr7035.banking.domain.usecases.ValidateCardNumberUseCase
import by.alexandr7035.banking.ui.extensions.getFormattedDate
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddCardViewModel(
    private val validateCardNumberUseCase: ValidateCardNumberUseCase
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

            is AddCardIntent.AddressFirstLineChanged -> {
                reduceFields(currentState.cardFields.copy(addressFirstLine = intent.addressLine))
            }

            is AddCardIntent.AddressSecondLineChanged -> {
                reduceFields(currentState.cardFields.copy(addressSecondLine = intent.addressLine))
            }

            is AddCardIntent.CardHolderChanged -> {
                reduceFields(currentState.cardFields.copy(cardHolder = intent.cardHolder))
            }

            is AddCardIntent.CardNumberChanged -> {
                reduceFields(currentState.cardFields.copy(cardNumber = intent.number))
            }

            is AddCardIntent.CvvCodeChanged -> {
                reduceFields(currentState.cardFields.copy(cvvCode = intent.code))
            }

            is AddCardIntent.ExpirationDateChanged -> {

                val formatted = if (intent.date == null) {
                    "-"
                }
                else {
                    intent.date.getFormattedDate("dd MMM yyyy")
                }

                reduceFields(currentState.cardFields.copy(expirationDate = formatted))
            }

            is AddCardIntent.SaveCard -> {
                _state.update { curr ->
                    curr.copy(
                        isLoading = true
                    )
                }

                viewModelScope.launch {
                    delay(2000)

                    _state.update { curr ->
                        curr.copy(
                            isLoading = false,
                            cardSavedEvent = triggered(true)
                        )
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

            is AddCardIntent.ToggleDatePicker -> {
                _state.update { curr ->
                    curr.copy(showDatePicker = intent.isShown)
                }
            }
        }
    }

    private fun reduceFields(
        cardFields: AddCardFormFields
    ) {
        _state.update { curr ->
            curr.copy(cardFields = cardFields)
        }
    }

    private fun reduceInitialMockState() {
        _state.update {
            AddCardState(
                cardFields = AddCardFormFields(
                    cardNumber = "2298 1268 3398 9874",
                    cardHolder = "Alexander Michael",
                    addressFirstLine = "2890 Pangandaran Street",
                    addressSecondLine = "",
                    cvvCode = "123",
                )
            )
        }
    }

}
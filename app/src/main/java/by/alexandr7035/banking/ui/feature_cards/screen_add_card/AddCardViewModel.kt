package by.alexandr7035.banking.ui.feature_cards.screen_add_card

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.BuildConfig
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddCardViewModel: ViewModel() {
    private val _state = MutableStateFlow(AddCardState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: AddCardIntent) {
        when (intent) {
            is AddCardIntent.EnterScreen -> {
                if (BuildConfig.DEBUG) {
                    reduceInitialMockState()
                }
            }
            is AddCardIntent.AddressFirstLineChanged -> {
                _state.update { curr ->
                    curr.copy(cardFields = curr.cardFields.copy(
                        addressFirstLine = intent.addressLine
                    ))
                }
            }
            is AddCardIntent.AddressSecondLineChanged -> {
                _state.update { curr ->
                    curr.copy(cardFields = curr.cardFields.copy(
                        addressSecondLine = intent.addressLine
                    ))
                }
            }
            is AddCardIntent.CardHolderChanged ->  {
                _state.update { curr ->
                    curr.copy(cardFields = curr.cardFields.copy(
                        cardHolder = intent.cardHolder
                    ))
                }
            }
            is AddCardIntent.CardNumberChanged -> {
                _state.update { curr ->
                    curr.copy(cardFields = curr.cardFields.copy(
                        cardNumber = intent.number
                    ))
                }
            }
            is AddCardIntent.CvvCodeChanged -> {
                _state.update { curr ->
                    curr.copy(cardFields = curr.cardFields.copy(
                        cvvCode = intent.code
                    ))
                }
            }
            is AddCardIntent.ExpirationDateChanged -> {
                TODO()
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

    private fun reduceField() {
        _state.update { cur ->
            cur.copy(cardFields = cur.cardFields.copy())
        }
    }
}
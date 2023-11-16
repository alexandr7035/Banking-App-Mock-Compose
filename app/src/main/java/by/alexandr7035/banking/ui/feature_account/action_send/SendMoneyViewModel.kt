package by.alexandr7035.banking.ui.feature_account.action_send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.account.send_money.GetSuggestedSendValuesForCardBalance
import by.alexandr7035.banking.domain.features.account.send_money.SendMoneyUseCase
import by.alexandr7035.banking.domain.features.cards.GetCardByIdUseCase
import by.alexandr7035.banking.domain.features.cards.GetDefaultCardUseCase
import by.alexandr7035.banking.domain.features.contacts.GetContactByIdUseCase
import by.alexandr7035.banking.domain.features.contacts.GetRecentContactUseCase
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SendMoneyViewModel(
    private val getSuggestedSendValuesForCardBalance: GetSuggestedSendValuesForCardBalance,
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val getDefaultCardUseCase: GetDefaultCardUseCase,
    private val getRecentContactUseCase: GetRecentContactUseCase,
    private val getContactByIdUseCase: GetContactByIdUseCase,
    private val sendMoneyUseCase: SendMoneyUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SendMoneyScreenState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: SendMoneyScreenIntent) {
        when (intent) {
            is SendMoneyScreenIntent.EnterScreen -> {
                reduceInitialCard(intent.selectedCardId)
                reduceInitialContact()
            }

            is SendMoneyScreenIntent.ChooseCard -> {
                _state.value.cardPickerState.selectedCard?.let {
                    reduceLoadCard(it.id)
                }
            }

            is SendMoneyScreenIntent.RefreshCard -> {
                _state.value.cardPickerState.selectedCard?.let {
                    reduceLoadCard(it.id)
                }
            }

            is SendMoneyScreenIntent.ToggleCardPicker -> {
                _state.update {
                    it.copy(
                        cardPickerState = it.cardPickerState.copy(
                            showCardPicker = intent.show
                        )
                    )
                }
            }

            is SendMoneyScreenIntent.UpdateSelectedValue -> {
                reduceChooseTopUpAmount(intent.amount)
            }

            is SendMoneyScreenIntent.ChooseContact -> {
                _state.update {
                    it.copy(
                        contactPickerState = it.contactPickerState.copy(
                            isLoading = true
                        )
                    )
                }

                viewModelScope.launch {
                    val contact = OperationResult.runWrapped {
                        getContactByIdUseCase.execute(intent.contactId)
                    }

                    when (contact) {
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                   contactPickerState = it.contactPickerState.copy(
                                        selectedContact = ContactUi.mapFromDomain(contact.data),
                                        isLoading = false
                                    )
                                )
                            }
                        }

                        is OperationResult.Failure -> {
                            _state.update {
                                // Left previous contact
                                it.copy(
                                    contactPickerState = it.contactPickerState.copy(
                                        isLoading = false,
                                        contactSelectedErrorEvent = triggered(contact.error.errorType)
                                    )
                                )
                            }
                        }
                    }
                }
            }

            is SendMoneyScreenIntent.ToggleContactPicker -> {
                _state.update {
                    it.copy(
                        contactPickerState = it.contactPickerState.copy(
                            showContactPicker = intent.show
                        )
                    )
                }
            }

            is SendMoneyScreenIntent.ProceedClick -> {
                // TODO
            }

            is SendMoneyScreenIntent.DismissSuccessDialog -> {
                _state.update {
                    it.copy(showSuccessDialog = false)
                }
            }
        }
    }

    private fun reduceInitialContact() {
        _state.update {
            it.copy(contactPickerState = it.contactPickerState.copy(isLoading = true))
        }

        viewModelScope.launch {
            val res = OperationResult.runWrapped {
                getRecentContactUseCase.execute()
            }

            when (res) {
                is OperationResult.Success -> {
                    _state.update {
                        it.copy(
                            contactPickerState = it.contactPickerState.copy(
                                isLoading = false,
                                selectedContact = res.data?.let { ContactUi.mapFromDomain(it) }
                            )
                        )
                    }
                }

                is OperationResult.Failure -> {
                    _state.update {
                        it.copy(
                            contactPickerState = it.contactPickerState.copy(
                                isLoading = false,
                                selectedContact = null
                            )
                        )
                    }
                }
            }

        }
    }

    private fun reduceChooseTopUpAmount(amount: MoneyAmount) {
        _state.update {
            it.copy(
                amountState = it.amountState.copy(
                    selectedAmount = amount
                )
            )
        }
    }

    private fun reduceInitialCard(selectedCardId: String?) {
        _state.update {
            it.copy(
                cardPickerState = it.cardPickerState.copy(
                    isLoading = true
                )
            )
        }

        viewModelScope.launch {
            val cardRes = if (selectedCardId == null) {
                OperationResult.runWrapped {
                    getDefaultCardUseCase.execute()
                }
            } else {
                OperationResult.runWrapped {
                    getCardByIdUseCase.execute(selectedCardId)
                }
            }

            when (cardRes) {
                is OperationResult.Success -> {
                    if (cardRes.data != null) {
                        _state.update {
                            it.copy(
                                cardPickerState = it.cardPickerState.copy(
                                    selectedCard = CardUi.mapFromDomain(cardRes.data),
                                    isLoading = false
                                )
                            )
                        }

                        reduceAmountPickerValues(cardRes.data.recentBalance)
                    } else {
                        _state.update {
                            it.copy(
                                cardPickerState = it.cardPickerState.copy(
                                    selectedCard = null,
                                    isLoading = false
                                )
                            )
                        }
                    }
                }

                is OperationResult.Failure -> {
                    _state.update {
                        it.copy(
                            cardPickerState = it.cardPickerState.copy(
                                selectedCard = null,
                                isLoading = false,
                            )
                        )
                    }
                }
            }
        }
    }

    private fun reduceLoadCard(cardId: String) {
        _state.update {
            it.copy(
                cardPickerState = it.cardPickerState.copy(
                    isLoading = true
                )
            )
        }

        viewModelScope.launch {
            val card = OperationResult.runWrapped {
                getCardByIdUseCase.execute(cardId)
            }

            when (card) {
                is OperationResult.Success -> {
                    _state.update {
                        it.copy(
                            cardPickerState = it.cardPickerState.copy(
                                selectedCard = CardUi.mapFromDomain(card.data),
                                isLoading = false
                            )
                        )
                    }

                    reduceAmountPickerValues(card.data.recentBalance)
                }

                is OperationResult.Failure -> {
                    _state.update {
                        it.copy(
                            cardPickerState = it.cardPickerState.copy(
                                selectedCard = null,
                                isLoading = false,
                                cardSelectErrorEvent = triggered(card.error.errorType)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun reduceAmountPickerValues(cardBalance: MoneyAmount) {
        val proposedSendValues = getSuggestedSendValuesForCardBalance.execute(cardBalance)
        _state.update {
            it.copy(
                amountState = it.amountState.copy(
                    proposedValues = proposedSendValues,
                    selectedAmount = proposedSendValues.firstOrNull() ?: MoneyAmount(0f)
                )
            )
        }
    }


    fun consumeLoadCardErrorEvent() {
        _state.update {
            it.copy(
                cardPickerState = it.cardPickerState.copy(
                    cardSelectErrorEvent = consumed()
                )
            )
        }
    }

    fun consumeLoadContactErrorEvent() {
        _state.update {
            it.copy(
                contactPickerState = it.contactPickerState.copy(
                    contactSelectedErrorEvent = consumed()
                )
            )
        }
    }
}
package by.alexandr7035.banking.ui.feature_account.action_send

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.account.send_money.GetSuggestedSendValuesForCardBalance
import by.alexandr7035.banking.domain.features.account.send_money.SendMoneyUseCase
import by.alexandr7035.banking.domain.features.cards.GetCardByIdUseCase
import by.alexandr7035.banking.domain.features.cards.GetDefaultCardUseCase
import by.alexandr7035.banking.domain.features.cards.model.PaymentCard
import by.alexandr7035.banking.domain.features.contacts.GetContactByIdUseCase
import by.alexandr7035.banking.domain.features.contacts.GetRecentContactUseCase
import by.alexandr7035.banking.ui.core.resources.UiText
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
                reduceLoadCard(
                    cardId = intent.selectedCardId, showErrorIfAny = false
                )

                reduceLoadContact(
                    contactId = null,
                    showErrorIfAny = false
                )
            }

            is SendMoneyScreenIntent.ChooseCard -> {
                reduceLoadCard(
                    cardId = intent.cardId, showErrorIfAny = true
                )
            }
//
//            is SendMoneyScreenIntent.RefreshCard -> {
//                _state.value.cardPickerState.selectedCard?.let {
//                    reduceLoadCard(it.id)
//                }
//            }

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
                reduceLoadContact(
                    contactId = intent.contactId,
                    showErrorIfAny = true
                )
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

    private fun reduceLoadContact(
        contactId: Long?,
        showErrorIfAny: Boolean
    ) {
        _state.update {
            it.copy(contactPickerState = it.contactPickerState.copy(isLoading = true))
        }

        viewModelScope.launch {
            val contactResult = if (contactId != null) {
                OperationResult.runWrapped {
                    getContactByIdUseCase.execute(contactId)
                }
            } else {
                OperationResult.runWrapped {
                    getRecentContactUseCase.execute()
                }
            }

            when (contactResult) {
                is OperationResult.Success -> {
                    _state.update {
                        it.copy(
                            contactPickerState = it.contactPickerState.copy(
                                isLoading = false,
                                selectedContact = contactResult.data?.let { ContactUi.mapFromDomain(it) }
                            )
                        )
                    }
                }

                is OperationResult.Failure -> {
                    _state.update {
                        it.copy(
                            contactPickerState = it.contactPickerState.copy(
                                isLoading = false,
                                selectedContact = null,
                            )
                        )
                    }

                    if (showErrorIfAny) {
                        _state.update {
                            it.copy(
                                contactPickerState = it.contactPickerState.copy(
                                    contactSelectedErrorEvent = triggered(contactResult.error.errorType)
                                )
                            )
                        }
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


    private fun reduceLoadCard(
        cardId: String?, showErrorIfAny: Boolean
    ) {
        _state.update {
            it.copy(
                cardPickerState = it.cardPickerState.copy(
                    isLoading = true
                )
            )
        }

        viewModelScope.launch {
            val cardResult = if (cardId == null) {
                OperationResult.runWrapped {
                    getDefaultCardUseCase.execute()
                }
            } else {
                OperationResult.runWrapped {
                    getCardByIdUseCase.execute(cardId)
                }
            }

            when (cardResult) {
                is OperationResult.Success -> {
                    if (cardResult.data != null) {
                        _state.update {
                            it.copy(
                                cardPickerState = it.cardPickerState.copy(
                                    selectedCard = CardUi.mapFromDomain(cardResult.data), isLoading = false
                                )
                            )
                        }

                        reduceAmountPickersState(card = cardResult.data)
                    } else {
                        _state.update {
                            it.copy(
                                cardPickerState = it.cardPickerState.copy(
                                    selectedCard = null, isLoading = false
                                )
                            )
                        }

                        reduceAmountPickersState(card = null)
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

                    if (showErrorIfAny) {
                        _state.update {
                            it.copy(
                                cardPickerState = it.cardPickerState.copy(
                                    cardSelectErrorEvent = triggered(cardResult.error.errorType)
                                )
                            )
                        }
                    }

                    reduceAmountPickersState(card = null)
                }
            }
        }
    }

    private fun reduceAmountPickersState(
        card: PaymentCard?
    ) {
        val proposedSendValues = card?.let { getSuggestedSendValuesForCardBalance.execute(it.recentBalance) } ?: emptySet()

        val balanceValue = card?.recentBalance?.value ?: 0f
        val showInsufficientBalance = card != null && balanceValue == 0F

        val insufficientBalanceError = if (showInsufficientBalance) {
            UiText.StringResource(R.string.insufficient_card_balance)
        }
        else {
            null
        }

        _state.update {
            it.copy(
                amountState = it.amountState.copy(
                    proposedValues = proposedSendValues,
                    selectedAmount = proposedSendValues.firstOrNull() ?: MoneyAmount(0f),
                    pickersEnabled = balanceValue > 0f,
                    error = insufficientBalanceError
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
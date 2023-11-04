package by.alexandr7035.banking.ui.feature_account.action_topup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.account.account_topup.GetSuggestedTopUpValuesUseCase
import by.alexandr7035.banking.domain.features.account.account_topup.TopUpAccountUseCase
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.cards.GetCardByIdUseCase
import by.alexandr7035.banking.domain.features.cards.GetDefaultCardUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopUpScreenViewModel(
    private val getSuggestedTopUpValuesUseCase: GetSuggestedTopUpValuesUseCase,
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val getDefaultCardUseCase: GetDefaultCardUseCase,
    private val topUpAccountUseCase: TopUpAccountUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TopUpScreenState())
    val state = _state.asStateFlow()

    init {
        val proposedTopUpValues = getSuggestedTopUpValuesUseCase.execute()
        _state.update {
            it.copy(
                amountState = it.amountState.copy(
                    proposedValues = proposedTopUpValues,
                    selectedAmount = proposedTopUpValues.first()
                )
            )
        }
    }

    fun emitIntent(intent: TopUpScreenIntent) {
        when (intent) {
            is TopUpScreenIntent.EnterScreen -> {
                reduceLoadDefaultCard()
            }

            is TopUpScreenIntent.UpdateSelectedValue -> {
                reduceChooseTopUpAmount(intent.amount)
            }

            is TopUpScreenIntent.ChooseCard -> {
                reduceLoadCard(intent.cardId)
            }

            TopUpScreenIntent.RefreshCard -> {
                _state.value.cardPickerState.selectedCard?.let {
                    reduceLoadCard(it.id)
                }
            }

            is TopUpScreenIntent.ToggleCardPicker -> {
                _state.update {
                    it.copy(
                        cardPickerState = it.cardPickerState.copy(
                            showCardPicker = intent.show
                        )
                    )
                }
            }

            is TopUpScreenIntent.ProceedClick -> {
                reduceStartTopUp()
            }

            is TopUpScreenIntent.DismissSuccessDialog -> {
                _state.update {
                    it.copy(showSuccessDialog = false)
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

    private fun reduceLoadDefaultCard() {
        _state.update {
            it.copy(
                cardPickerState = it.cardPickerState.copy(
                    isLoading = true
                )
            )
        }

        viewModelScope.launch {
            val defaultCardRes = OperationResult.runWrapped {
                getDefaultCardUseCase.execute()
            }

            when (defaultCardRes) {
                is OperationResult.Success -> {
                    if (defaultCardRes.data != null) {
                        _state.update {
                            it.copy(
                                cardPickerState = it.cardPickerState.copy(
                                    selectedCard = CardUi.mapFromDomain(defaultCardRes.data),
                                    isLoading = false
                                )
                            )
                        }
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

    private fun reduceChooseTopUpAmount(amount: MoneyAmount) {
        _state.update {
            it.copy(
                amountState = it.amountState.copy(
                    selectedAmount = amount
                )
            )
        }
    }

    private fun reduceStartTopUp() {
        _state.update {
            it.copy(
                isLoading = true
            )
        }

        viewModelScope.launch {
            val topUpRes: OperationResult<Unit> = OperationResult.runWrapped {

                _state.value.cardPickerState.selectedCard?.let {
                    topUpAccountUseCase.execute(
                        cardId = it.id,
                        amount = _state.value.amountState.selectedAmount
                    )
                }

            }

            when (topUpRes) {
                is OperationResult.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            showSuccessDialog = true,
                        )
                    }
                }

                is OperationResult.Failure -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = topUpRes.error.errorType.asUiTextError()
                        )
                    }
                }
            }
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
}
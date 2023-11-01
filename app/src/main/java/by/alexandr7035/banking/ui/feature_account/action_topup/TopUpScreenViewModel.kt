package by.alexandr7035.banking.ui.feature_account.action_topup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.account.account_topup.GetSuggestedTopUpValuesUseCase
import by.alexandr7035.banking.domain.features.cards.GetCardByIdUseCase
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TopUpScreenViewModel(
    private val getSuggestedTopUpValuesUseCase: GetSuggestedTopUpValuesUseCase,
    private val getCardByIdUseCase: GetCardByIdUseCase
): ViewModel() {
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
            is TopUpScreenIntent.UpdateSelectedValue -> {
                _state.update {
                    it.copy(
                        amountState = it.amountState.copy(
                            selectedAmount = intent.amount
                        )
                    )
                }
            }

            is TopUpScreenIntent.ChooseCard -> {
                _state.update {
                    it.copy(
                        cardPickerState = it.cardPickerState.copy(
                            isLoading = true
                        )
                    )
                }

                viewModelScope.launch {
                    val card = OperationResult.runWrapped {
                        getCardByIdUseCase.execute(intent.cardId)
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

            is TopUpScreenIntent.ToggleCardPicker -> {
                _state.update {
                    it.copy(
                        cardPickerState = it.cardPickerState.copy(
                            showCardPicker = intent.show
                        )
                    )
                }
            }

            TopUpScreenIntent.ProceedClick -> {
                _state.update {
                    it.copy(
                        isLoading = true
                    )
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
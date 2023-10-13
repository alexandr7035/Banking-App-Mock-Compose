package by.alexandr7035.banking.ui.feature_cards.screen_card_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.usecases.cards.GetAllCardsUseCase
import by.alexandr7035.banking.ui.components.error.UiError
import by.alexandr7035.banking.ui.extensions.getFormattedDate
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CardListViewModel(
    private val getAllCardsUseCase: GetAllCardsUseCase
): ViewModel() {
    private val _state: MutableStateFlow<CardListState> = MutableStateFlow(
        CardListState.Loading
    )

    val state = _state.asStateFlow()

    fun emitIntent(intent: CardListIntent) {
        when (intent) {
            is CardListIntent.EnterScreen -> load()
        }
    }

    private fun load() {
        viewModelScope.launch {
            reduceLoading()

            val res = OperationResult.runWrapped {
                getAllCardsUseCase.execute()
            }

            when (res) {
                is OperationResult.Success -> {
                    val cardsUi = res.data.map {
                        val date = it.expiration.getFormattedDate("MM/yy")

                        CardUi(
                            cardNumber = it.cardNumber,
                            expiration = date,
                            balance = "\$${it.usdBalance}$"
                        )
                    }

                    reduceData(cardsUi)
                }

                is OperationResult.Failure -> {
                    reduceError(UiError.fromDomainError(res.error.errorType))
                }
            }

        }
    }

    private fun reduceLoading() {
        _state.update {
            CardListState.Loading
        }
    }

    private fun reduceError(uiError: UiError) {
        _state.update {
            CardListState.Error(uiError)
        }
    }

    private fun reduceData(cards: List<CardUi>) {
        _state.update {
            CardListState.Success(cards)
        }
    }
}
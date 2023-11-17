package by.alexandr7035.banking.ui.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.account.GetCardBalanceObservableUseCase
import by.alexandr7035.banking.domain.features.account.GetTotalAccountBalanceUseCase
import by.alexandr7035.banking.domain.features.cards.GetHomeCardsUseCase
import by.alexandr7035.banking.domain.features.profile.GetCompactProfileUseCase
import by.alexandr7035.banking.domain.features.savings.GetHomeSavingsUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_home.model.HomeIntent
import by.alexandr7035.banking.ui.feature_home.model.HomeState
import by.alexandr7035.banking.ui.feature_profile.ProfileUi
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCompactProfileUseCase: GetCompactProfileUseCase,
    private val getHomeCardsUseCase: GetHomeCardsUseCase,
    private val getHomeSavingsUseCase: GetHomeSavingsUseCase,
    private val getTotalAccountBalanceUseCase: GetTotalAccountBalanceUseCase,
    private val getCardBalanceObservableUseCase: GetCardBalanceObservableUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState.Loading
    )

    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, exception ->
        reduceError(ErrorType.fromThrowable(exception))
    }

    fun emitIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.EnterScreen -> loadData()
        }
    }

    private fun loadData() {
        _state.update {
            HomeState.Loading
        }

        viewModelScope.launch(errorHandler) {
            val profileJob = async() {
                val res = getCompactProfileUseCase.execute()
                ProfileUi.mapFromDomain(res)
            }

            val cardsJob = async() {
                val res = getHomeCardsUseCase.execute()

                res.map {
                    CardUi.mapFromDomain(
                        card = it,
                        balanceFlow = getCardBalanceFlow(it.cardId)
                    )
                }
            }

            val savingsJob = async() {
                val res = getHomeSavingsUseCase.execute()

                res.map {
                    SavingUi.mapFromDomain(it)
                }
            }

            val profile = profileJob.await()
            val cards = cardsJob.await()
            val saving = savingsJob.await()

            val balanceFlow = getTotalAccountBalanceUseCase.execute()
                .map { accountBalance ->
                    MoneyAmountUi.mapFromDomain(accountBalance)
                }
                .catch {
                    reduceError(ErrorType.fromThrowable(it))
                }

            // Success state
            reduceData(
                profile = profile,
                cards = cards,
                savings = saving,
                balance = balanceFlow
            )
        }
    }

    private fun reduceData(
        profile: ProfileUi,
        cards: List<CardUi>,
        savings: List<SavingUi>,
        balance: Flow<MoneyAmountUi>,
    ) {
        _state.update {
            HomeState.Success(
                profile = profile,
                cards = cards,
                savings = savings,
                balance = balance
            )
        }
    }

    private fun reduceError(error: ErrorType) {
        _state.update {
            HomeState.Error(
                error = error.asUiTextError()
            )
        }
    }

    private suspend fun getCardBalanceFlow(cardId: String): Flow<String> {
        return getCardBalanceObservableUseCase.execute(cardId)
            .map {
                MoneyAmountUi.mapFromDomain(it).amountStr
            }
            .catch {
                reduceError(ErrorType.fromThrowable(it))
            }
    }
}
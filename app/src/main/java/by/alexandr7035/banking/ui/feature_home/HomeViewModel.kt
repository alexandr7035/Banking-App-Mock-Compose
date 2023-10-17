package by.alexandr7035.banking.ui.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.data.profile.Profile
import by.alexandr7035.banking.data.profile.ProfileRepository
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.usecases.cards.GetHomeCardsUseCase
import by.alexandr7035.banking.domain.usecases.savings.GetHomeSavingsUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_home.model.HomeIntent
import by.alexandr7035.banking.ui.feature_home.model.HomeState
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val profileRepository: ProfileRepository,
    private val getHomeCardsUseCase: GetHomeCardsUseCase,
    private val getHomeSavingsUseCase: GetHomeSavingsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState.Loading
    )

    val state = _state.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception) {
            is AppError -> {
                reduceError(exception.errorType)
            }

            else -> {
                reduceError(ErrorType.UNKNOWN_ERROR)
            }
        }
    }

    private fun loadData() {
        _state.update {
            HomeState.Loading
        }

        viewModelScope.launch(coroutineExceptionHandler) {

            val profileJob = async() {
                profileRepository.getProfile()
            }

            val cardsJob = async() {
                val res = getHomeCardsUseCase.execute()

                res.map {
                    CardUi.mapFromDomain(it)
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

            reduceData(profile, cards, saving)
        }
    }

    private fun reduceData(
        profile: Profile,
        cards: List<CardUi>,
        savings: List<SavingUi>
    ) {
        _state.update {
            HomeState.Success(
                profile = profile,
                cards = cards,
                savings = savings
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

    fun emitIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.EnterScreen -> loadData()
        }
    }
}
package by.alexandr7035.banking.ui.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.data.profile.Profile
import by.alexandr7035.banking.data.profile.ProfileRepository
import by.alexandr7035.banking.ui.components.error.UiError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
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
) : ViewModel() {

    private val _state: MutableStateFlow<HomeState> = MutableStateFlow(
        HomeState.Loading
    )

    val state = _state.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        reduceError(exception)
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
                delay(1500)
                // TODO repo
                List(1) {
                    CardUi.mock()
                }
            }

            val savingsJob = async() {
                delay(1000)
                // TODO repo
                List(3) {
                    SavingUi.mock()
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

    // TODO business logic errors
    private fun reduceError(error: Throwable) {
        _state.update {
            HomeState.Error(
                error = UiError(
                    title = "An error occurred",
                    message = "Error: ${error.message}"
                )
            )
        }
    }

    fun emitIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.EnterScreen -> loadData()
        }
    }
}
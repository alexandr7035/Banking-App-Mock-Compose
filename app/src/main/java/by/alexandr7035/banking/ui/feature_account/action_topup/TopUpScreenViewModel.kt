package by.alexandr7035.banking.ui.feature_account.action_topup

import androidx.lifecycle.ViewModel
import by.alexandr7035.banking.domain.features.account.account_topup.GetSuggestedTopUpValuesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TopUpScreenViewModel(
    private val getSuggestedTopUpValuesUseCase: GetSuggestedTopUpValuesUseCase
): ViewModel() {
    private val _state = MutableStateFlow(TopUpScreenState())
    val state = _state.asStateFlow()

    init {
        val proposedTopUpValues = getSuggestedTopUpValuesUseCase.execute()
        _state.update {
            it.copy(
                proposedValues = proposedTopUpValues,
            )
        }
    }

    fun emitIntent(intent: TopUpScreenIntent) {
        when (intent) {
            is TopUpScreenIntent.UpdateSelectedValue -> {
                _state.update {
                    it.copy(
                        selectedAmount = intent.amount
                    )
                }
            }

            is TopUpScreenIntent.ChooseCard -> {

            }

            TopUpScreenIntent.ProceedClick -> {
                TODO()
            }
        }
    }
}
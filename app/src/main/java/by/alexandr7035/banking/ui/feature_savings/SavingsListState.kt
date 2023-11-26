package by.alexandr7035.banking.ui.feature_savings

import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi

sealed class SavingsListState {
    data class Success(
        val savings: List<SavingUi>,
    ) : SavingsListState()

    data class Error(val error: UiText) : SavingsListState()

    object Loading : SavingsListState()
}

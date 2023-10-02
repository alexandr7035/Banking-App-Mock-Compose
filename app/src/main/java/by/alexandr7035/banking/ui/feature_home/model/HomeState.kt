package by.alexandr7035.banking.ui.feature_home.model

import by.alexandr7035.banking.data.profile.Profile
import by.alexandr7035.banking.ui.components.error.UiError
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi

sealed class HomeState() {
    // Single loading for all parts of screen for simplicity
    object Loading: HomeState()

    data class Success(
        val profile: Profile,
        val cards: List<CardUi> = emptyList(),
        val savings: List<SavingUi> = emptyList(),
    ): HomeState()

    data class Error(val error: UiError): HomeState()
}

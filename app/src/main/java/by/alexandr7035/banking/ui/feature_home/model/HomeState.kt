package by.alexandr7035.banking.ui.feature_home.model

import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_cards.model.CardUi
import by.alexandr7035.banking.ui.feature_profile.ProfileUi
import by.alexandr7035.banking.ui.feature_savings.model.SavingUi

sealed class HomeState() {
    // Single loading for all parts of screen for simplicity
    object Loading: HomeState()

    data class Success(
        val profile: ProfileUi,
        val cards: List<CardUi> = emptyList(),
        val savings: List<SavingUi> = emptyList(),
    ): HomeState()

    data class Error(val error: UiText): HomeState()
}

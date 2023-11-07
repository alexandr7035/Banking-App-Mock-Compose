package by.alexandr7035.banking.ui.feature_account.action_topup

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount

sealed class TopUpScreenIntent {
    data class EnterScreen(val selectedCardId: String?): TopUpScreenIntent()
    data class ChooseCard(val cardId: String): TopUpScreenIntent()
    object RefreshCard: TopUpScreenIntent()
    data class UpdateSelectedValue(val amount: MoneyAmount): TopUpScreenIntent()
    data class ToggleCardPicker(val show: Boolean): TopUpScreenIntent()
    object ProceedClick: TopUpScreenIntent()
    object DismissSuccessDialog: TopUpScreenIntent()
}

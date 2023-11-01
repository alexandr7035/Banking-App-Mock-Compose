package by.alexandr7035.banking.ui.feature_account.action_topup

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount

sealed class TopUpScreenIntent {
    data class ChooseCard(val cardId: String): TopUpScreenIntent()
    data class UpdateSelectedValue(val amount: MoneyAmount): TopUpScreenIntent()
    object ProceedClick: TopUpScreenIntent()
}

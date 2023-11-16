package by.alexandr7035.banking.ui.feature_account.action_send

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount

sealed class SendMoneyScreenIntent {
    data class EnterScreen(val selectedCardId: String?) : SendMoneyScreenIntent()
    data class ChooseCard(val cardId: String) : SendMoneyScreenIntent()
    data class ToggleContactPicker(val show: Boolean): SendMoneyScreenIntent()
    data class ChooseContact(val contactId: Long) : SendMoneyScreenIntent()
//    object RefreshCard : SendMoneyScreenIntent()
    data class UpdateSelectedValue(val amount: MoneyAmount) : SendMoneyScreenIntent()
    data class ToggleCardPicker(val show: Boolean) : SendMoneyScreenIntent()
    object ProceedClick : SendMoneyScreenIntent()
    object DismissSuccessDialog : SendMoneyScreenIntent()
}
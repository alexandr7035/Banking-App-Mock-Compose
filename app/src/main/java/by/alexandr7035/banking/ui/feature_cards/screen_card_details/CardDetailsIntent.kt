package by.alexandr7035.banking.ui.feature_cards.screen_card_details

sealed class CardDetailsIntent {
    data class EnterScreen(val cardNumber: String): CardDetailsIntent()
    data class ToggleDeleteCardDialog(val isDialogShown: Boolean): CardDetailsIntent()
    object ConfirmDeleteCard: CardDetailsIntent()
    object ConsumeCardDeletedEvent: CardDetailsIntent()
}

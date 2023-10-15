package by.alexandr7035.banking.ui.feature_cards.screen_card_details

sealed class CardDetailsIntent {
    data class EnterScreen(val cardNumber: String): CardDetailsIntent()
    object RequestDeleteCard: CardDetailsIntent()
    object ConfirmDeleteCard: CardDetailsIntent()
    object ConsumeCardDeletedEvent: CardDetailsIntent()
}

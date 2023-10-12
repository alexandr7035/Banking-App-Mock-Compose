package by.alexandr7035.banking.ui.feature_cards.screen_add_card

sealed class AddCardIntent {
    object EnterScreen : AddCardIntent()
    data class CardNumberChanged(val number: String) : AddCardIntent()
    data class CvvCodeChanged(val code: String) : AddCardIntent()
    data class ExpirationDateChanged(val date: Long?) : AddCardIntent()
    data class CardHolderChanged(val cardHolder: String) : AddCardIntent()
    data class AddressFirstLineChanged(val addressLine: String) : AddCardIntent()
    data class AddressSecondLineChanged(val addressLine: String) : AddCardIntent()
    object SaveCard : AddCardIntent()
    object ConsumeResultEvent : AddCardIntent()
    data class ToggleDatePicker(val isShown: Boolean) : AddCardIntent()
}

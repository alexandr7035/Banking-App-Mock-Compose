package by.alexandr7035.banking.ui.feature_cards.screen_add_card

sealed class AddCardIntent {
    object EnterScreen : AddCardIntent()
    data class StringFieldChanged(
        val fieldType: AddCardFieldType,
        val fieldValue: String
    ) : AddCardIntent()
    data class ExpirationPickerSet(val date: Long?) : AddCardIntent()
    object SaveCard : AddCardIntent()
    object ConsumeResultEvent : AddCardIntent()
    data class ToggleDatePicker(val isShown: Boolean) : AddCardIntent()
}

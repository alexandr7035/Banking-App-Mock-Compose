package by.alexandr7035.banking.ui.feature_cards.screen_add_card

data class AddCardFormFields(
    val cardNumber: String = "",
    val cardHolder: String = "",
    val addressFirstLine: String = "",
    val addressSecondLine: String = "",
    val cvvCode: String = "",
    val expirationDate: Long? = null ,
)

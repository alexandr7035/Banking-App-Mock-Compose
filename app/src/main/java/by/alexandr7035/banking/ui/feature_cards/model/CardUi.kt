package by.alexandr7035.banking.ui.feature_cards.model

data class CardUi(
    val cardNumber: String,
    val expiration: String,
    val balance: String,
) {
    companion object {
        fun mock(): CardUi {
            return CardUi(
                cardNumber = "2298126833989874",
                expiration = "02/24",
                balance = "\$2887.65"
            )
        }
    }
}

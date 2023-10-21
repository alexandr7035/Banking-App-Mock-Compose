package by.alexandr7035.banking.domain.features.cards.model

data class PaymentCard(
    val cardId: String,
    val cardNumber: String,
    val cardHolder: String,
    val expiration: Long,
    val usdBalance: Float,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val addedDate: Long
)

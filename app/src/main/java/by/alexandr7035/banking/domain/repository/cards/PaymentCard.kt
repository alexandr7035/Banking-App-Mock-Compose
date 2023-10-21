package by.alexandr7035.banking.domain.repository.cards

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

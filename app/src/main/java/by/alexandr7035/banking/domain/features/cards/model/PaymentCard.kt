package by.alexandr7035.banking.domain.features.cards.model

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount

data class PaymentCard(
    val cardId: String,
    val isPrimary: Boolean,
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val expiration: Long,
    val recentBalance: MoneyAmount,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val addedDate: Long
)

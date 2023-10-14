package by.alexandr7035.banking.ui.feature_cards.model

import by.alexandr7035.banking.domain.repository.cards.PaymentCard
import by.alexandr7035.banking.ui.core.extensions.getFormattedDate

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

        fun mapFromDomain(card: PaymentCard): CardUi {
            val date = card.expiration.getFormattedDate("MM/yy")

            return CardUi(
                cardNumber = card.cardNumber,
                expiration = date,
                balance = "\$${card.usdBalance}$"
            )
        }
    }
}

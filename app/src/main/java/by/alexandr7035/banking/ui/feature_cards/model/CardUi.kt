package by.alexandr7035.banking.ui.feature_cards.model

import by.alexandr7035.banking.domain.repository.cards.PaymentCard
import by.alexandr7035.banking.ui.core.extensions.getFormattedDate

data class CardUi(
    // TODO Consider adding some ID (which guaranteed to be unformatted)
    val cardNumber: String,
    val expiration: String,
    val balance: String,
    val addressFirstLine: String,
    val addressSecondLine: String?,
    val dateAdded: String
) {
    companion object {
        fun mock(): CardUi {
            return CardUi(
                cardNumber = "2298126833989874",
                expiration = "02/24",
                balance = "\$2887.65",
                addressFirstLine = "2890 Pangandaran Street",
                addressSecondLine = null,
                dateAdded = "12 Jan 2021 22:12"
            )
        }

        fun mapFromDomain(card: PaymentCard): CardUi {
            val date = card.expiration.getFormattedDate("MM/yy")

            return CardUi(
                cardNumber = card.cardNumber,
                expiration = date,
                balance = "\$${card.usdBalance}$",
                addressFirstLine = card.addressFirstLine,
                addressSecondLine = card.addressSecondLine.ifBlank { null },
                dateAdded = card.addedDate.getFormattedDate("dd MMM yyyy HH:mm"),
            )
        }
    }
}

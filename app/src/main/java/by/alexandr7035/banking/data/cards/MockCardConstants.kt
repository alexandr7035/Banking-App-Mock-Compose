package by.alexandr7035.banking.data.cards

import by.alexandr7035.banking.domain.features.cards.model.CardType

object MockCardConstants {
    private val cards: Map<String, CardType> = mapOf(
        // Visa
        "2298126833989874" to CardType.DEBIT,
        // Mastercard
        "5555555555554444" to CardType.CREDIT,
        // American Express
        "4111111111111111" to CardType.DEBIT,
        // Discover
        "6011111111111117" to CardType.CREDIT
    )

    fun randomCard(): Pair<String, CardType> {
        return cards.toList().random()
    }

    fun cardTypeByNumber(cardNumber: String): CardType? {
        return cards[cardNumber]
    }
}
package by.alexandr7035.banking.data.cards

import by.alexandr7035.banking.domain.features.cards.model.CardType

object MockCardConstants {
    // https://github.com/drmonkeyninja/test-payment-cards/blob/master/readme.md
    private val cards: Map<String, CardType> = mapOf(
        // Mastercard
        "2298126833989874" to CardType.CREDIT,
        "5555555555554444" to CardType.DEBIT,
        // VISA
        "4111111111111111" to CardType.DEBIT,
        // Maestro
        "6304000000000000" to CardType.DEBIT
    )

    fun randomCard(): Pair<String, CardType> {
        return cards.toList().random()
    }

    fun cardTypeByNumber(cardNumber: String): CardType? {
        return cards[cardNumber]
    }
}
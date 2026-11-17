package by.alexandr7035.banking

import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.cards.cache.CardsDao

/**
 * In-memory replacement for the Room [CardsDao] used in JVM unit tests.
 * Each method mirrors the behavior of the real SQL query.
 */
class FakeCardsDao(initialCards: List<CardEntity> = emptyList()) : CardsDao {

    // Key = card number (the primary key in Room)
    private val cards = initialCards.associateBy { it.number }.toMutableMap()

    override suspend fun addCard(card: CardEntity) {
        // Mirrors OnConflictStrategy.ABORT
        require(card.number !in cards) { "Card ${card.number} already exists" }
        cards[card.number] = card
    }

    override suspend fun getCards(): List<CardEntity> =
        cards.values.sortedBy { it.number }

    override suspend fun getCardByNumber(number: String): CardEntity? = cards[number]

    override suspend fun deleteCard(card: CardEntity) {
        cards.remove(card.number)
    }

    override suspend fun updateCard(card: CardEntity) {
        // Room @Update ignores rows that don't exist
        if (card.number in cards) cards[card.number] = card
    }

    override suspend fun markCardAsPrimary(cardId: String) {
        cards.replaceAll { number, card -> card.copy(isPrimary = number == cardId) }
    }

    override suspend fun unmarkCardAsPrimary(cardId: String) {
        cards[cardId]?.let { cards[cardId] = it.copy(isPrimary = false) }
    }
}

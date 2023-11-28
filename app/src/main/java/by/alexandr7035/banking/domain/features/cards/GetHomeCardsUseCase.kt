package by.alexandr7035.banking.domain.features.cards

import by.alexandr7035.banking.domain.features.cards.model.PaymentCard

class GetHomeCardsUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(): List<PaymentCard> {
        val allCards = cardsRepository.getCards()

        val (primary, other) = allCards.partition { it.isPrimary }

        val sortedPrimary = primary.sortedByDescending { it.addedDate }
        val sortedOther = other.sortedByDescending { it.addedDate }

        return (sortedPrimary + sortedOther).take(DISPLAYED_COUNT)
    }

    companion object {
        private const val DISPLAYED_COUNT = 3
    }
}
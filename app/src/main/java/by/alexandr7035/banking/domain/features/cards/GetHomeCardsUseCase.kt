package by.alexandr7035.banking.domain.features.cards

import by.alexandr7035.banking.domain.features.cards.model.PaymentCard

class GetHomeCardsUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(): List<PaymentCard> {
        val all = cardsRepository.getCards()

        val primary = all
            .filter { it.isPrimary }
            .sortedByDescending { it.addedDate }

        val other = all
            .filter { !it.isPrimary }
            .sortedByDescending { it.addedDate }

        return (primary + other).take(DISPLAYED_COUNT)
    }

    companion object {
        private const val DISPLAYED_COUNT = 2
    }
}
package by.alexandr7035.banking.domain.usecases.cards

import by.alexandr7035.banking.domain.repository.cards.CardsRepository
import by.alexandr7035.banking.domain.repository.cards.PaymentCard

class GetHomeCardsUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(): List<PaymentCard> {
        return cardsRepository.getCards().sortedByDescending {
            it.addedDate
        }.take(DISPLAYED_COUNT)
    }

    companion object {
        private const val DISPLAYED_COUNT = 2
    }
}
package by.alexandr7035.banking.domain.features.cards

import by.alexandr7035.banking.domain.features.cards.model.PaymentCard

class GetAllCardsUseCase(private val cardsRepository: CardsRepository) {
    suspend fun execute(): List<PaymentCard> {
        return cardsRepository.getCards().sortedByDescending {
            it.addedDate
        }
    }
}
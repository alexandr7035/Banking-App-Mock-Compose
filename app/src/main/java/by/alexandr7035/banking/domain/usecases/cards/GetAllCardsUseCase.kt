package by.alexandr7035.banking.domain.usecases.cards

import by.alexandr7035.banking.domain.repository.cards.CardsRepository
import by.alexandr7035.banking.domain.repository.cards.PaymentCard

class GetAllCardsUseCase(private val cardsRepository: CardsRepository) {
    suspend fun execute(): List<PaymentCard> {
        return cardsRepository.getCards().sortedBy {
            it.expiration
        }
    }
}
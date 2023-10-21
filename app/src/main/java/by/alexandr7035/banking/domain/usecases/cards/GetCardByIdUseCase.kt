package by.alexandr7035.banking.domain.usecases.cards

import by.alexandr7035.banking.domain.repository.cards.CardsRepository
import by.alexandr7035.banking.domain.repository.cards.PaymentCard

class GetCardByIdUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardNumber: String): PaymentCard {
        return cardsRepository.getCardById(cardNumber)
    }
}
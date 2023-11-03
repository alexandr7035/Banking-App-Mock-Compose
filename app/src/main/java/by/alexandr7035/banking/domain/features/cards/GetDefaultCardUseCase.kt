package by.alexandr7035.banking.domain.features.cards

import by.alexandr7035.banking.domain.features.cards.model.PaymentCard

class GetDefaultCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(): PaymentCard? {
        // TODO default card logic
        return cardsRepository.getCards().firstOrNull()
    }
}
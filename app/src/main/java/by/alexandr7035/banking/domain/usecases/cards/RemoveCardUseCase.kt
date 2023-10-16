package by.alexandr7035.banking.domain.usecases.cards

import by.alexandr7035.banking.domain.repository.cards.CardsRepository

class RemoveCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(cardNumber: String) {
        cardsRepository.deleteCardByNumber(cardNumber)
    }
}
package by.alexandr7035.banking.domain.usecases.cards

import by.alexandr7035.banking.domain.repository.cards.AddCardPayload
import by.alexandr7035.banking.domain.repository.cards.CardsRepository

class AddCardUseCase(
    private val cardsRepository: CardsRepository
) {
    suspend fun execute(payload: AddCardPayload) {
        cardsRepository.addCard(payload)
    }
}
package by.alexandr7035.banking.domain.features.cards

import by.alexandr7035.banking.domain.features.cards.model.AddCardPayload
import by.alexandr7035.banking.domain.features.cards.model.PaymentCard

interface CardsRepository {
    suspend fun getCards(): List<PaymentCard>
    suspend fun addCard(data: AddCardPayload)
    suspend fun getCardById(id: String): PaymentCard
    suspend fun deleteCardById(id: String)
    suspend fun markCardAsPrimary(cardId: String, isPrimary: Boolean = false)
    suspend fun getPrimaryCard(): PaymentCard?
}
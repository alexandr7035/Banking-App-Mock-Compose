package by.alexandr7035.banking.domain.repository.cards

interface CardsRepository {
    suspend fun getCards(): List<PaymentCard>
    suspend fun addCard(data: AddCardPayload)
    suspend fun getCardById(id: String): PaymentCard
    suspend fun deleteCardByNumber(number: String)
}
package by.alexandr7035.banking.domain.repository.cards

interface CardsRepository {
    suspend fun getCards(): List<PaymentCard>
    suspend fun addCard(data: AddCardPayload)
    suspend fun getCardByNumber(number: String): PaymentCard
}
package by.alexandr7035.banking.data.cards

import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.repository.cards.AddCardPayload
import by.alexandr7035.banking.domain.repository.cards.CardsRepository
import by.alexandr7035.banking.domain.repository.cards.PaymentCard
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class CardsRepositoryMock(
    private val cacheDao: CardsDao,
    private val coroutineDispatcher: CoroutineDispatcher
) : CardsRepository {
    override suspend fun getCards(): List<PaymentCard> = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY)

        return@withContext cacheDao.getCards().map { cardEntity ->
            mapCachedCardToDomain(cardEntity)
        }
    }

    override suspend fun addCard(data: AddCardPayload) = withContext(coroutineDispatcher) {
        val card = cacheDao.getCardByNumber(data.cardNumber)

        if (card == null) {
            delay(MOCK_DELAY)

            val entity = mapAddCardPayloadToCache(data)
            cacheDao.addCard(entity)
        }
        else {
            throw AppError(ErrorType.CARD_ALREADY_ADDED)
        }
    }

    override suspend fun getCardByNumber(number: String): PaymentCard = withContext(coroutineDispatcher) {
        val cardEntity = cacheDao.getCardByNumber(number) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        return@withContext mapCachedCardToDomain(cardEntity)
    }

    private fun mapCachedCardToDomain(cardEntity: CardEntity) = PaymentCard(
        cardNumber = cardEntity.number,
        cardHolder = cardEntity.cardHolder,
        addressFirstLine = cardEntity.addressFirstLine,
        addressSecondLine = cardEntity.addressSecondLine,
        expiration = cardEntity.expiration,
        addedDate = cardEntity.addedDate,

        // Mock value here
        usdBalance = 2000F,
    )

    private fun mapAddCardPayloadToCache(addCardPayload: AddCardPayload): CardEntity = CardEntity(
        number = addCardPayload.cardNumber,
        cardHolder = addCardPayload.cardHolder,
        addressFirstLine = addCardPayload.addressFirstLine,
        addressSecondLine = addCardPayload.addressSecondLine,
        expiration = addCardPayload.expirationDate,
        addedDate = System.currentTimeMillis()
    )

    override suspend fun deleteCardByNumber(number: String) {
        val cardEntity = cacheDao.getCardByNumber(number) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        cacheDao.deleteCard(cardEntity)
    }

    companion object {
        private const val MOCK_DELAY = 500L
    }
}
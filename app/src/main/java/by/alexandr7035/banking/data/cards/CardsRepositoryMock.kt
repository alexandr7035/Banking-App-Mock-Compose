package by.alexandr7035.banking.data.cards

import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.cards.model.AddCardPayload
import by.alexandr7035.banking.domain.features.cards.CardsRepository
import by.alexandr7035.banking.domain.features.cards.model.CardType
import by.alexandr7035.banking.domain.features.cards.model.PaymentCard
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

    override suspend fun getCardById(id: String): PaymentCard = withContext(coroutineDispatcher) {
        val cardEntity = cacheDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        return@withContext mapCachedCardToDomain(cardEntity)
    }

    private fun mapCachedCardToDomain(cardEntity: CardEntity) = PaymentCard(
        cardId = cardEntity.number,
        cardNumber = cardEntity.number,
        cardHolder = cardEntity.cardHolder,
        addressFirstLine = cardEntity.addressFirstLine,
        addressSecondLine = cardEntity.addressSecondLine,
        expiration = cardEntity.expiration,
        addedDate = cardEntity.addedDate,
        recentBalance = MoneyAmount(cardEntity.recentBalance),
        cardType = cardEntity.cardType
    )

    private fun mapAddCardPayloadToCache(addCardPayload: AddCardPayload): CardEntity = CardEntity(
        number = addCardPayload.cardNumber,
        cardHolder = addCardPayload.cardHolder,
        addressFirstLine = addCardPayload.addressFirstLine,
        addressSecondLine = addCardPayload.addressSecondLine,
        expiration = addCardPayload.expirationDate,
        addedDate = System.currentTimeMillis(),
        recentBalance = MOCK_CARD_INITIAL_BALANCE,
        cardType = CardType.DEBIT
    )

    override suspend fun deleteCardById(id: String) {
        val cardEntity = cacheDao.getCardByNumber(id) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        cacheDao.deleteCard(cardEntity)
    }

    override suspend fun topUpCard(cardId: String, amount: MoneyAmount) {
        val cardEntity = cacheDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        delay(MOCK_DELAY)
        val updated = cardEntity.copy(recentBalance = cardEntity.recentBalance + amount.value)
        cacheDao.updateCard(updated)
    }

    companion object {
        private const val MOCK_DELAY = 500L
        private const val MOCK_CARD_INITIAL_BALANCE = 0f
    }
}
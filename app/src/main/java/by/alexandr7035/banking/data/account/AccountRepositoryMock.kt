package by.alexandr7035.banking.data.account

import by.alexandr7035.banking.core.extensions.sumFloat
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AccountRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    // For mock app simply use last cached card balances
    private val cardsDao: CardsDao,
) : AccountRepository {

    override fun getBalanceFlow(): Flow<MoneyAmount> {
        // For mock app simply sum card balances
        return flow {
            // The flow starts afresh every time it is collected
            // A simple way to imitate server polling for balance
            while (true) {
                emit(calculateBalance())
                delay(MOCK_OBSERVING_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun getCardBalanceFlow(cardId: String): Flow<MoneyAmount> {
        // Check card exists
        cardsDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_NOT_FOUND)

        return flow {
            while (true) {
                // For mock app emit last card balance saved in db
                val card = cardsDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_HAS_BEEN_DELETED)
                emit(MoneyAmount(card.recentBalance))

                delay(MOCK_OBSERVING_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun topUpCard(cardId: String, amount: MoneyAmount) {
        val cardEntity = cardsDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        val updated = cardEntity.copy(recentBalance = cardEntity.recentBalance + amount.value)
        cardsDao.updateCard(updated)
    }

    override suspend fun sendFromCard(cardId: String, amount: MoneyAmount, contactId: Long) {
        val cardEntity = cardsDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        if (cardEntity.recentBalance < amount.value) throw AppError(ErrorType.INSUFFICIENT_CARD_BALANCE)
        val updated = cardEntity.copy(recentBalance = cardEntity.recentBalance - amount.value)
        cardsDao.updateCard(updated)
    }

    private suspend fun calculateBalance(): MoneyAmount {
        val amount = cardsDao.getCards().sumFloat {
            it.recentBalance
        }

        return MoneyAmount(amount)
    }

    companion object {
        private const val MOCK_OBSERVING_DELAY = 5000L
    }
}
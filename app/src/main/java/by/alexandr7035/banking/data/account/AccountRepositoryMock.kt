package by.alexandr7035.banking.data.account

import by.alexandr7035.banking.data.helpers.sumMoneyAmounts
import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.cards.CardsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AccountRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val cardsRepository: CardsRepository,
) : AccountRepository {

    override fun getBalanceFlow(): Flow<MoneyAmount> {
        // For mock app simply sum card balances
        return flow {
            // The flow starts afresh every time it is collected
            // A simple way to imitate server polling for balance
            while (true) {
                emit(calculateBalance())
                delay(MOCK_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    private suspend fun calculateBalance(): MoneyAmount {
        val amount = cardsRepository.getCards().sumMoneyAmounts {
            it.recentBalance
        }

        return amount
    }

    companion object {
        private const val MOCK_DELAY = 5000L
    }
}
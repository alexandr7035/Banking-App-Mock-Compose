package by.alexandr7035.banking.data.account

import android.util.Log
import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.account.model.AccountBalance
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.random.Random

class AccountRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher
) : AccountRepository {

    override fun getBalanceFlow(): Flow<AccountBalance> {
        return flow {
            // The flow starts afresh every time it is collected
            delay(200)

            Log.d("DEBUG_FLOW", "initial balance $INITIAL_MOCK_BALANCE")
            emit(AccountBalance(INITIAL_MOCK_BALANCE))

            // A simple way to imitate server polling for balance
            while (true) {
                val mockBalanceDiff = Random.nextDouble(-100.0, 100.0)
                val balance = INITIAL_MOCK_BALANCE + mockBalanceDiff
                Log.d("DEBUG_FLOW", "changed balance $balance")

                emit(AccountBalance(balance))
                delay(MOCK_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    companion object {
        private const val MOCK_DELAY = 5000L
        private const val INITIAL_MOCK_BALANCE = 3000.0
    }
}
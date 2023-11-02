package by.alexandr7035.banking.domain.features.account

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    fun getBalanceFlow(): Flow<MoneyAmount>
}
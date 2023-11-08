package by.alexandr7035.banking.domain.features.account

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import kotlinx.coroutines.flow.Flow

class GetTotalAccountBalanceUseCase(
    private val accountRepository: AccountRepository
) {
    fun execute(): Flow<MoneyAmount> {
        return accountRepository.getBalanceFlow()
    }
}
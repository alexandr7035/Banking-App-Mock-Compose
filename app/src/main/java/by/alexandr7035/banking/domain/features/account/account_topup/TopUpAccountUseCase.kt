package by.alexandr7035.banking.domain.features.account.account_topup

import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.cards.CardsRepository

class TopUpAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(cardId: String, amount: MoneyAmount) {
        return accountRepository.topUpCard(
            cardId, amount
        )
    }
}
package by.alexandr7035.banking.domain.features.account.account_topup

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.transactions.TransactionRepository
import by.alexandr7035.banking.domain.features.transactions.model.TransactionRowPayload
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

class TopUpAccountUseCase(
    private val transactionRepository: TransactionRepository,
) {
    suspend fun execute(cardId: String, amount: MoneyAmount) {
        return transactionRepository.submitTransaction(
            TransactionRowPayload(
                type = TransactionType.TOP_UP,
                amount = amount,
                contactId = null,
                cardId = cardId
            )
        )
    }
}
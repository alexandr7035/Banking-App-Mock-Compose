package by.alexandr7035.banking.domain.features.account.send_money

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.transactions.TransactionRepository
import by.alexandr7035.banking.domain.features.transactions.model.TransactionRowPayload
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

class SendMoneyUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(
        amount: MoneyAmount,
        fromCardId: String,
        contactId: Long,
    ) {
        return transactionRepository.submitTransaction(
            TransactionRowPayload(
                type = TransactionType.SEND,
                amount = amount,
                cardId = fromCardId,
                contactId = contactId
            )
        )
    }
}
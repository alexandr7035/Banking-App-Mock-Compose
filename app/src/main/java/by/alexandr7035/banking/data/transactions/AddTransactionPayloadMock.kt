package by.alexandr7035.banking.data.transactions

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

data class AddTransactionPayloadMock(
    val type: TransactionType,
    val value: MoneyAmount,
    val linkedContact: Contact?,
)

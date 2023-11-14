package by.alexandr7035.banking.domain.features.transactions.model

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.contacts.Contact

data class Transaction(
    val id: Long,
    val type: TransactionType,
    val value: MoneyAmount,
    val linkedContact: Contact
)

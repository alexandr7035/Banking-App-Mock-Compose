package by.alexandr7035.banking.ui.feature_transactions.model

import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.core.extensions.getFormattedDate
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi

data class TransactionUi(
    val id: Long,
    val type: TransactionType,
    val status: TransactionStatus,
    val value: MoneyAmountUi,
    val transactionDate: String,
    val contact: ContactUi?
) {
    companion object {
        fun mock(
            id: Long = 0,
            type: TransactionType = TransactionType.SEND,
            status: TransactionStatus = TransactionStatus.COMPLETED,
            transactionDate: String = "13 Oct 2021",
            value: MoneyAmountUi = MoneyAmountUi("$200.50"),
            contact: ContactUi? = ContactUi.mock()
        ): TransactionUi {
            return TransactionUi(
                id = id,
                type = type,
                transactionDate = transactionDate,
                status = status,
                value = value,
                contact = contact
            )
        }

        fun mapFromDomain(transaction: Transaction): TransactionUi {
            val contact = if (transaction.linkedContact != null) {
                ContactUi.mapFromDomain(transaction.linkedContact)
            } else {
                null
            }

            return TransactionUi(
                id = transaction.id,
                value = MoneyAmountUi.mapFromDomain(transaction.value),
                transactionDate = transaction.createdDate.getFormattedDate("dd MMM yyyy HH:mm"),
                contact = contact,
                type = transaction.type,
                status = transaction.recentStatus
            )
        }
    }
}

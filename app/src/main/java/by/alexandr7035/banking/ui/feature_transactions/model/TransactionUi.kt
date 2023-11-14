package by.alexandr7035.banking.ui.feature_transactions.model

import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi

data class TransactionUi(
    val id: Long,
    val transactionDate: String,
    val contact: ContactUi
) {
    companion object {
        fun mock(): TransactionUi {
            return TransactionUi(
                id = 0,
                transactionDate = "13 Oct 2021",
                contact = ContactUi.mock()
            )
        }

        fun mapFromDomain(transaction: Transaction): TransactionUi {
            return TransactionUi(
                id = transaction.id,
                transactionDate = "13 Oct 2021",
                contact = ContactUi.mapFromDomain(transaction.linkedContact)
            )
        }
    }
}

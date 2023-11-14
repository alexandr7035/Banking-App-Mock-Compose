package by.alexandr7035.banking.ui.feature_transactions.model

import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.ui.core.extensions.getFormattedDate
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi

data class TransactionUi(
    val id: Long,
    val value: MoneyAmountUi,
    val transactionDate: String,
    val contact: ContactUi?
) {
    companion object {
        fun mock(): TransactionUi {
            return TransactionUi(
                id = 0,
                transactionDate = "13 Oct 2021",
                value = MoneyAmountUi("$200.50"),
                contact = ContactUi.mock()
            )
        }

        fun mapFromDomain(transaction: Transaction): TransactionUi {
            val contact = if (transaction.linkedContact != null) {
                ContactUi.mapFromDomain(transaction.linkedContact)
            }
            else {
                null
            }

            return TransactionUi(
                id = transaction.id,
                value = MoneyAmountUi.mapFromDomain(transaction.value),
                transactionDate = transaction.createdDate.getFormattedDate("dd MMM yyyy HH:mm"),
                contact = contact,
            )
        }
    }
}

package by.alexandr7035.banking.testdata

import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.contacts.ContactsRepository

class TestContactsRepository(
    private val contact: Contact
) : ContactsRepository {

    override suspend fun getContacts(): List<Contact> {
        return listOf(contact)
    }

    override suspend fun getContactById(contactId: Long): Contact {
        if (contactId != contact.id) {
            throw AppError(ErrorType.GENERIC_NOT_FOUND_ERROR)
        }
        return contact
    }

    override suspend fun getContactFromQr(qrCode: String): Contact {
        if (qrCode != "by.alexandr7035.banking:addcontact") {
            throw AppError(ErrorType.USER_NOT_FOUND)
        }
        return contact
    }
}

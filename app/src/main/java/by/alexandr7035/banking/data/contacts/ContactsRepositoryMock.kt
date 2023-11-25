package by.alexandr7035.banking.data.contacts

import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.contacts.ContactsRepository
import kotlinx.coroutines.delay

class ContactsRepositoryMock: ContactsRepository {
    override suspend fun getContacts(): List<Contact> {
        delay(MOCK_DELAY)

        return listOf(
            Contact(
                id = 0,
                name = "Yulisa Meyun",
                profilePic = "https://api.dicebear.com/7.x/personas/svg?seed=dsf423",
                linkedCardNumber = "4001020000000009"
            ),
            Contact(
                id = 1,
                name = "Fanny Alison",
                profilePic = "https://api.dicebear.com/7.x/personas/svg?seed=Maggie",
                linkedCardNumber = "4646464646464644"
            ),
            Contact(
                id = 2,
                name = "Andi Taher",
                profilePic = "https://api.dicebear.com/7.x/personas/svg?seed=dsf42332",
                linkedCardNumber = "4444333322221111"
            ),
        )
    }

    override suspend fun getContactById(contactId: Long): Contact {
        return getContacts().find {
            it.id == contactId
        } ?: throw AppError(ErrorType.GENERIC_NOT_FOUND_ERROR)
    }

    override suspend fun getContactFromQr(qrCode: String): Contact {
        return when (qrCode) {
            MOCK_CONTACT_QR -> getContacts().random()
            else -> throw AppError(ErrorType.USER_NOT_FOUND)
        }
    }

    companion object {
        private const val MOCK_DELAY = 200L
        private const val MOCK_CONTACT_QR = "by.alexandr7035.banking:addcontact"
    }
}
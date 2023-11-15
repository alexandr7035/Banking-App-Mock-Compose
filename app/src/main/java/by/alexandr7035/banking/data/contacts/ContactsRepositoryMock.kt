package by.alexandr7035.banking.data.contacts

import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.contacts.ContactsRepository

class ContactsRepositoryMock: ContactsRepository {
    override fun getContacts(): List<Contact> {
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

    override fun getContactById(contactId: Long): Contact {
        return getContacts().find {
            it.id == contactId
        } ?: throw AppError(ErrorType.GENERIC_NOT_FOUND_ERROR)
    }
}
package by.alexandr7035.banking.domain.features.contacts

interface ContactsRepository {
    fun getContacts(): List<Contact>

    fun getContactById(contactId: Long): Contact
}
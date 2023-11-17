package by.alexandr7035.banking.domain.features.contacts

interface ContactsRepository {
    suspend fun getContacts(): List<Contact>

    suspend fun getContactById(contactId: Long): Contact
}
package by.alexandr7035.banking.domain.features.contacts

class GetContactsUseCase(
    private val contactsRepository: ContactsRepository
) {
    fun execute(): List<Contact> {
        return contactsRepository.getContacts()
    }
}
package by.alexandr7035.banking.domain.features.contacts

class GetContactByIdUseCase(
    private val contactsRepository: ContactsRepository
) {
    fun execute(contactId: Long): Contact {
        return contactsRepository.getContactById(contactId)
    }
}
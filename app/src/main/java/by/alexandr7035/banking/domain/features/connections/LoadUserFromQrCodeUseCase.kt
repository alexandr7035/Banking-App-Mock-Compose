package by.alexandr7035.banking.domain.features.connections

import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.contacts.ContactsRepository

class LoadUserFromQrCodeUseCase(
    private val contactsRepository: ContactsRepository
) {
    suspend fun execute(qrCode: String): Contact {
        return contactsRepository.getContacts().random()
    }
}
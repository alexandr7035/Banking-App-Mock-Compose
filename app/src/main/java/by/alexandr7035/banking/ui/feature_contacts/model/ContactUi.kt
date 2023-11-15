package by.alexandr7035.banking.ui.feature_contacts.model

import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.ui.core.extensions.splitStringWithDivider

// TODO
data class ContactUi(
    val name: String,
    val id: Long,
    val cardNumber: String,
    val profilePictureUrl: String
) {
    companion object {
        fun mock() = ContactUi(
            name = "Vina Andini",
            id = 0,
            cardNumber = "0000111122223333",
            profilePictureUrl = ""
        )

        fun mapFromDomain(contact: Contact) = ContactUi(
            name = contact.name,
            id = contact.id,
            cardNumber = "0000111122223333",
            profilePictureUrl = contact.profilePic
        )
    }
}

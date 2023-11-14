package by.alexandr7035.banking.ui.feature_contacts.model

import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.ui.core.extensions.splitStringWithDivider

// TODO
data class ContactUi(
    val name: String,
    val id: String,
    val profilePictureUrl: String
) {
    companion object {
        fun mock() = ContactUi(
            name = "Vina Andini",
            id = "0821 2103 1120",
            profilePictureUrl = ""
        )

        fun mapFromDomain(contact: Contact) = ContactUi(
            name = contact.name,
            id = contact.id.splitStringWithDivider(),
            profilePictureUrl = contact.profilePic
        )
    }
}

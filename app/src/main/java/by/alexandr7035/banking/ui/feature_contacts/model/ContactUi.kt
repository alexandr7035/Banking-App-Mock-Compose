package by.alexandr7035.banking.ui.feature_contacts.model

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
    }
}

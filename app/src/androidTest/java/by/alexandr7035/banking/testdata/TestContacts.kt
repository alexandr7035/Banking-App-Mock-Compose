package by.alexandr7035.banking.testdata

import by.alexandr7035.banking.domain.features.contacts.Contact

object TestContacts {

    fun andi(): Contact = Contact(
        id = 2L,
        name = "Andi Taher",
        profilePic = "",
        linkedCardNumber = "4444333322221111"
    )

    fun yulisa(): Contact = Contact(
        id = 0L,
        name = "Yulisa Meyun",
        profilePic = "",
        linkedCardNumber = "4001020000000009"
    )
}

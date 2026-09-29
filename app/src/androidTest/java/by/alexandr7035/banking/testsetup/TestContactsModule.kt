package by.alexandr7035.banking.testsetup

import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.contacts.ContactsRepository
import by.alexandr7035.banking.testdata.TestContactsRepository
import org.koin.dsl.module

fun testContactsModule(contact: Contact) = module {
    single<ContactsRepository> {
        TestContactsRepository(contact)
    }
}

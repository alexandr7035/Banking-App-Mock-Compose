package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.contacts.GetContactByIdUseCase
import by.alexandr7035.banking.domain.features.contacts.GetContactsUseCase
import by.alexandr7035.banking.domain.features.contacts.GetRecentContactUseCase
import org.koin.dsl.module

val contactsModule = module {
    factory { GetContactsUseCase(get()) }
    factory { GetContactByIdUseCase(get()) }
    factory { GetRecentContactUseCase(get()) }
}
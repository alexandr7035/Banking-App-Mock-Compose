package by.alexandr7035.banking.core.di

import by.alexandr7035.banking.core.di.data.dataModule
import by.alexandr7035.banking.core.di.data.workerModule
import by.alexandr7035.banking.core.di.domain.accountUseCasesModule
import by.alexandr7035.banking.core.di.domain.appLockUseCasesModule
import by.alexandr7035.banking.core.di.domain.cardUseCasesModule
import by.alexandr7035.banking.core.di.domain.connectionsModule
import by.alexandr7035.banking.core.di.domain.contactsModule
import by.alexandr7035.banking.core.di.domain.loginUseCasesModule
import by.alexandr7035.banking.core.di.domain.onboardingModule
import by.alexandr7035.banking.core.di.domain.otpUseCasesModule
import by.alexandr7035.banking.core.di.domain.profileUseCasesModule
import by.alexandr7035.banking.core.di.domain.savingsUseCasesModule
import by.alexandr7035.banking.core.di.domain.signUpModule
import by.alexandr7035.banking.core.di.domain.transactionsModule
import by.alexandr7035.banking.core.di.domain.validationUseCasesModule
import by.alexandr7035.banking.core.di.presentation.presentationModule
import org.koin.dsl.module

val appModule = module {
    includes(appLockUseCasesModule)

    includes(loginUseCasesModule)
    includes(signUpModule)

    includes(cardUseCasesModule)
    includes(validationUseCasesModule)
    includes(savingsUseCasesModule)
    includes(profileUseCasesModule)
    includes(onboardingModule)
    includes(otpUseCasesModule)
    includes(accountUseCasesModule)
    includes(transactionsModule)
    includes(contactsModule)
    includes(connectionsModule)

    includes(dataModule)
    includes(presentationModule)
    includes(workerModule)
}
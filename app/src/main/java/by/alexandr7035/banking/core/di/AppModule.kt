package by.alexandr7035.banking.core

import by.alexandr7035.banking.core.di.data.dataModule
import by.alexandr7035.banking.core.di.domain.cardUseCasesModule
import by.alexandr7035.banking.core.di.domain.loginUseCasesModule
import by.alexandr7035.banking.core.di.domain.onboardingModule
import by.alexandr7035.banking.core.di.domain.profileUseCasesModule
import by.alexandr7035.banking.core.di.domain.savingsUseCasesModule
import by.alexandr7035.banking.core.di.domain.validationUseCasesModule
import by.alexandr7035.banking.core.di.presentation.presentationModule
import org.koin.dsl.module

val appModule = module {
    includes(cardUseCasesModule)
    includes(validationUseCasesModule)
    includes(loginUseCasesModule)
    includes(savingsUseCasesModule)
    includes(profileUseCasesModule)
    includes(onboardingModule)

    includes(dataModule)
    includes(presentationModule)
}
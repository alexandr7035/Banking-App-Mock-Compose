package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.usecases.profile.GetCompactProfileUseCase
import org.koin.dsl.module

val profileUseCasesModule = module {
    factory {  GetCompactProfileUseCase(profileRepository = get()) }
}
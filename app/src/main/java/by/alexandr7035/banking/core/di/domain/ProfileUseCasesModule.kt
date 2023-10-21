package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.profile.GetCompactProfileUseCase
import org.koin.dsl.module

val profileUseCasesModule = module {
    factory {  GetCompactProfileUseCase(profileRepository = get()) }
}
package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.onboarding.CheckIfPassedOnboardingUseCase
import by.alexandr7035.banking.domain.features.onboarding.PassOnboardingUseCase
import org.koin.dsl.module

val onboardingModule = module {
    factory {
        CheckIfPassedOnboardingUseCase(
            settignsRepository = get()
        )
    }
    factory {
        PassOnboardingUseCase(
            settignsRepository = get()
        )
    }
}
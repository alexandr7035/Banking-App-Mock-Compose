package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.signup.ConfirmSignUpUseCase
import by.alexandr7035.banking.domain.features.signup.SignUpWithEmailUseCase
import org.koin.dsl.module

val signUpModule = module {
    factory { ConfirmSignUpUseCase(signUpRepository = get()) }
    factory { SignUpWithEmailUseCase(signUpRepository = get()) }
}
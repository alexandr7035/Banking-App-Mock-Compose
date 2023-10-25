package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.signup.ConfirmSignUpWithEmailUseCase
import by.alexandr7035.banking.domain.features.signup.SignUpWithEmailUseCase
import org.koin.dsl.module

val signUpModule = module {
    factory { ConfirmSignUpWithEmailUseCase(signUpRepository = get()) }
    factory { SignUpWithEmailUseCase(signUpRepository = get()) }
}
package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.usecases.login.CheckIfLoggedInUseCase
import by.alexandr7035.banking.domain.usecases.login.LoginWithEmailUseCase
import by.alexandr7035.banking.domain.usecases.login.LogoutUseCase
import org.koin.dsl.module

val loginUseCasesModule = module {
    factory { LoginWithEmailUseCase(loginRepository = get()) }
    factory { LogoutUseCase(loginRepository = get()) }
    factory { CheckIfLoggedInUseCase(loginRepository = get()) }
}
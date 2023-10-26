package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.app_lock.AuthenticateWithPinUseCase
import org.koin.dsl.module

val appLockUseCasesModule = module {
    factory { AuthenticateWithPinUseCase(
        appLockRepository = get()
    ) }
}
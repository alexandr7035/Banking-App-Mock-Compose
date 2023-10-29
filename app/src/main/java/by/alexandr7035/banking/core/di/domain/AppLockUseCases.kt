package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.app_lock.AuthenticateWithPinUseCase
import by.alexandr7035.banking.domain.features.app_lock.CheckAppLockUseCase
import by.alexandr7035.banking.domain.features.app_lock.SetupAppLockUseCase
import org.koin.dsl.module

val appLockUseCasesModule = module {
    factory { AuthenticateWithPinUseCase(
        appLockRepository = get()
    ) }

    factory { CheckAppLockUseCase(
        appLockRepository = get()
    ) }

    factory {
        SetupAppLockUseCase(
            appLockRepository = get()
        )
    }
}
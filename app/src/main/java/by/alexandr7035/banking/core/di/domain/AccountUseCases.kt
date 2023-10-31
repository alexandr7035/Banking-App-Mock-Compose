package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.account.GetTotalAccountBalanceUseCase
import org.koin.dsl.module

val accountUseCasesModule = module {
    factory {
        GetTotalAccountBalanceUseCase(
            accountRepository = get()
        )
    }
}
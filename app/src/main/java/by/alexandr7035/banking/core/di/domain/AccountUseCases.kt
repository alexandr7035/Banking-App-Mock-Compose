package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.account.GetTotalAccountBalanceUseCase
import by.alexandr7035.banking.domain.features.account.account_topup.GetSuggestedTopUpValuesUseCase
import org.koin.dsl.module

val accountUseCasesModule = module {
    factory {
        GetTotalAccountBalanceUseCase(
            accountRepository = get()
        )
    }

    factory {
        GetSuggestedTopUpValuesUseCase()
    }
}
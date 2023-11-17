package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.account.GetTotalAccountBalanceUseCase
import by.alexandr7035.banking.domain.features.account.account_topup.GetSuggestedTopUpValuesUseCase
import by.alexandr7035.banking.domain.features.account.account_topup.TopUpAccountUseCase
import by.alexandr7035.banking.domain.features.account.send_money.GetSuggestedSendValuesForCardBalance
import by.alexandr7035.banking.domain.features.account.send_money.SendMoneyUseCase
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

    factory {
        GetSuggestedSendValuesForCardBalance()
    }

    factory {
        TopUpAccountUseCase(
            transactionRepository = get()
        )
    }

    factory {
        SendMoneyUseCase(
            transactionRepository = get()
        )
    }
}
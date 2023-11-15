package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.transactions.GetTransactionsUseCase
import by.alexandr7035.banking.domain.features.transactions.ObserveTransactionStatusUseCase
import org.koin.dsl.module

val transactionsModule = module {
    factory { GetTransactionsUseCase(get()) }
    factory { ObserveTransactionStatusUseCase(get()) }
}
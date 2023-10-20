package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.usecases.savings.GetAllSavingsUseCase
import by.alexandr7035.banking.domain.usecases.savings.GetHomeSavingsUseCase
import by.alexandr7035.banking.domain.usecases.savings.GetSavingByIdUseCase
import org.koin.dsl.module

val savingsUseCasesModule = module {
    factory { GetAllSavingsUseCase(savingsRepository = get()) }
    factory { GetHomeSavingsUseCase(savingsRepository = get()) }
    factory { GetSavingByIdUseCase(savingsRepository = get()) }
}
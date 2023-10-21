package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.savings.GetAllSavingsUseCase
import by.alexandr7035.banking.domain.features.savings.GetHomeSavingsUseCase
import by.alexandr7035.banking.domain.features.savings.GetSavingByIdUseCase
import org.koin.dsl.module

val savingsUseCasesModule = module {
    factory { GetAllSavingsUseCase(savingsRepository = get()) }
    factory { GetHomeSavingsUseCase(savingsRepository = get()) }
    factory { GetSavingByIdUseCase(savingsRepository = get()) }
}
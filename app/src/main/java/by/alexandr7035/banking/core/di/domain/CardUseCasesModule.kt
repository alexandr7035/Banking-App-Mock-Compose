package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.usecases.cards.AddCardUseCase
import by.alexandr7035.banking.domain.usecases.cards.GetAllCardsUseCase
import by.alexandr7035.banking.domain.usecases.cards.GetCardByNumberUseCase
import by.alexandr7035.banking.domain.usecases.cards.GetHomeCardsUseCase
import by.alexandr7035.banking.domain.usecases.cards.RemoveCardUseCase
import org.koin.dsl.module

val cardUseCasesModule = module {
    factory { GetAllCardsUseCase(get()) }
    factory { AddCardUseCase(get()) }
    factory { GetHomeCardsUseCase(get()) }
    factory { GetCardByNumberUseCase(get()) }
    factory { RemoveCardUseCase(get()) }
}
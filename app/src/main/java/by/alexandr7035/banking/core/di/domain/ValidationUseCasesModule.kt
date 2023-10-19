package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.usecases.validation.ValidateBillingAddressUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardExpirationUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardHolderUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardNumberUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCvvCodeUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateEmailUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidatePasswordUseCase
import org.koin.dsl.module

val validationUseCasesModule = module {
    factory { ValidateCardNumberUseCase() }
    factory { ValidateCvvCodeUseCase() }
    factory { ValidateCardExpirationUseCase() }
    factory { ValidateBillingAddressUseCase() }
    factory { ValidateCardHolderUseCase() }
    factory { ValidatePasswordUseCase() }
    factory { ValidateEmailUseCase() }
}
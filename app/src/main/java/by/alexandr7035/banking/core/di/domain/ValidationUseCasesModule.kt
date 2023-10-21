package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.validation.ValidateBillingAddressUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCardExpirationUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCardHolderUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCardNumberUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateCvvCodeUseCase
import by.alexandr7035.banking.domain.features.validation.ValidateEmailUseCase
import by.alexandr7035.banking.domain.features.validation.ValidatePasswordUseCase
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
package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.qr_codes.GenerateQrCodeUseCase
import org.koin.dsl.module

val connectionsModule = module {
    factory { GenerateQrCodeUseCase() }
}
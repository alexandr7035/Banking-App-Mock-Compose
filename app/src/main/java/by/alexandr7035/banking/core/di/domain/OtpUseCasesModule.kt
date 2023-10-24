package by.alexandr7035.banking.core.di.domain

import by.alexandr7035.banking.domain.features.otp.RequestOtpGenerationUseCase
import by.alexandr7035.banking.domain.features.otp.VerifyOtpUseCase
import org.koin.dsl.module

val otpUseCasesModule = module {
    factory { RequestOtpGenerationUseCase(
        otpRepository = get()
    ) }

    factory {
        VerifyOtpUseCase(
            otpRepository = get()
        )
    }
}
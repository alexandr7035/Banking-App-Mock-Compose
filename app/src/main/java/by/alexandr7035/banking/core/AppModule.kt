package by.alexandr7035.banking.core

import by.alexandr7035.banking.data.app.AppRepository
import by.alexandr7035.banking.data.app.AppRepositoryImpl
import by.alexandr7035.banking.data.login.LoginRepository
import by.alexandr7035.banking.data.login.LoginRepositoryImpl
import by.alexandr7035.banking.data.profile.ProfileRepository
import by.alexandr7035.banking.data.profile.ProfileRepositoryMock
import by.alexandr7035.banking.domain.usecases.validation.ValidateBillingAddressUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardExpirationUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardHolderUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardNumberUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCvvCodeUseCase
import by.alexandr7035.banking.ui.core.AppViewModel
import by.alexandr7035.banking.ui.error.ValidationErrorMapper
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.AddCardViewModel
import by.alexandr7035.banking.ui.feature_cards.screen_card_list.CardListViewModel
import by.alexandr7035.banking.ui.feature_home.model.HomeViewModel
import by.alexandr7035.banking.ui.feature_login.LoginViewModel
import by.alexandr7035.banking.ui.feature_profile.ProfileViewModel
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { AppViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { HomeViewModel(get()) }
    viewModel { CardListViewModel() }

    viewModel { AddCardViewModel(
        validateCardNumberUseCase = get(),
        validateCvvCodeUseCase = get(),
        validateCardExpirationUseCase = get(),
        validateCardHolderUseCase = get(),
        validateBillingAddressUseCase = get(),
        validationErrorMapper = get()
    ) }

    // Use cases
    factory { ValidateCardNumberUseCase() }
    factory { ValidateCvvCodeUseCase() }
    factory { ValidateCardExpirationUseCase() }
    factory { ValidateBillingAddressUseCase() }
    factory { ValidateCardHolderUseCase() }


    single {
        ValidationErrorMapper()
    }

    single<AppRepository> {
        AppRepositoryImpl(get())
    }

    single<LoginRepository> {
        LoginRepositoryImpl()
    }

    single<ProfileRepository> {
        ProfileRepositoryMock(Dispatchers.IO)
    }

    single {
        KsPrefs(androidApplication().applicationContext)
    }

}
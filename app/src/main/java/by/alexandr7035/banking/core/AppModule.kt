package by.alexandr7035.banking.core

import androidx.room.Room
import by.alexandr7035.banking.data.app.AppRepository
import by.alexandr7035.banking.data.app.AppRepositoryImpl
import by.alexandr7035.banking.data.cards.CardsRepositoryMock
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.data.db.CacheDatabase
import by.alexandr7035.banking.data.login.LoginRepository
import by.alexandr7035.banking.data.login.LoginRepositoryMock
import by.alexandr7035.banking.data.profile.ProfileRepository
import by.alexandr7035.banking.data.profile.ProfileRepositoryMock
import by.alexandr7035.banking.data.savings.SavingsRepositoryMock
import by.alexandr7035.banking.domain.repository.cards.CardsRepository
import by.alexandr7035.banking.domain.repository.savings.SavingsRepository
import by.alexandr7035.banking.domain.usecases.cards.AddCardUseCase
import by.alexandr7035.banking.domain.usecases.cards.GetAllCardsUseCase
import by.alexandr7035.banking.domain.usecases.cards.GetCardByNumberUseCase
import by.alexandr7035.banking.domain.usecases.cards.GetHomeCardsUseCase
import by.alexandr7035.banking.domain.usecases.cards.RemoveCardUseCase
import by.alexandr7035.banking.domain.usecases.login.LoginWithEmailUseCase
import by.alexandr7035.banking.domain.usecases.savings.GetAllSavingsUseCase
import by.alexandr7035.banking.domain.usecases.savings.GetHomeSavingsUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateBillingAddressUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardExpirationUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardHolderUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCardNumberUseCase
import by.alexandr7035.banking.domain.usecases.validation.ValidateCvvCodeUseCase
import by.alexandr7035.banking.ui.core.AppViewModel
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.AddCardViewModel
import by.alexandr7035.banking.ui.feature_cards.screen_card_details.CardDetailsViewModel
import by.alexandr7035.banking.ui.feature_cards.screen_card_list.CardListViewModel
import by.alexandr7035.banking.ui.feature_home.HomeViewModel
import by.alexandr7035.banking.ui.feature_login.LoginViewModel
import by.alexandr7035.banking.ui.feature_profile.ProfileViewModel
import by.alexandr7035.banking.ui.feature_savings.SavingsViewModel
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

// TODO separate module
val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { AppViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel {
        HomeViewModel(
            profileRepository = get(),
            getHomeCardsUseCase = get(),
            getHomeSavingsUseCase = get()
        )
    }
    viewModel { CardListViewModel(getAllCardsUseCase = get()) }
    viewModel {
        CardDetailsViewModel(
            getCardByNumberUseCase = get(),
            deleteCardByNumberUseCase = get()
        )
    }

    viewModel {
        AddCardViewModel(
            validateCardNumberUseCase = get(),
            validateCvvCodeUseCase = get(),
            validateCardExpirationUseCase = get(),
            validateCardHolderUseCase = get(),
            validateBillingAddressUseCase = get(),
            addCardUseCase = get()
        )
    }

    viewModel {
        SavingsViewModel(
            getAllSavingsUseCase = get()
        )
    }

    // Use cases
    factory { ValidateCardNumberUseCase() }
    factory { ValidateCvvCodeUseCase() }
    factory { ValidateCardExpirationUseCase() }
    factory { ValidateBillingAddressUseCase() }
    factory { ValidateCardHolderUseCase() }

    factory { GetAllCardsUseCase(cardsRepository = get()) }
    factory { AddCardUseCase(cardsRepository = get()) }
    factory { GetHomeCardsUseCase(cardsRepository = get()) }
    factory { GetCardByNumberUseCase(cardsRepository = get()) }
    factory { RemoveCardUseCase(cardsRepository = get()) }

    factory { GetAllSavingsUseCase(savingsRepository = get()) }
    factory { GetHomeSavingsUseCase(savingsRepository = get()) }
    factory { LoginWithEmailUseCase(loginRepository = get()) }

    single<CacheDatabase> {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            CacheDatabase::class.java,
            "cache.db"
        ).fallbackToDestructiveMigration().build()
    }

    single<CardsDao> {
        val db: CacheDatabase = get()
        db.getCardsDao()
    }

    single<AppRepository> {
        AppRepositoryImpl(get())
    }

    single<LoginRepository> {
        LoginRepositoryMock(
            coroutineDispatcher = Dispatchers.IO
        )
    }

    single<ProfileRepository> {
        ProfileRepositoryMock(dispatcher = Dispatchers.IO)
    }

    single<CardsRepository> {
        CardsRepositoryMock(
            cacheDao = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    single<SavingsRepository> {
        SavingsRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            context = androidApplication().applicationContext
        )
    }

    single {
        KsPrefs(androidApplication().applicationContext)
    }

}
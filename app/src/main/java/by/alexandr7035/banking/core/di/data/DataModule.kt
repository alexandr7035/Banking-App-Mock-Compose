package by.alexandr7035.banking.core.di.data

import androidx.room.Room
import by.alexandr7035.banking.data.app.AppRepositoryImpl
import by.alexandr7035.banking.data.app.AppSettignsRepository
import by.alexandr7035.banking.data.cards.CardsRepositoryMock
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.data.db.CacheDatabase
import by.alexandr7035.banking.data.login.LoginRepositoryMock
import by.alexandr7035.banking.data.otp.OtpRepositoryMock
import by.alexandr7035.banking.data.profile.ProfileRepositoryMock
import by.alexandr7035.banking.data.savings.SavingsRepositoryMock
import by.alexandr7035.banking.data.signup.SignUpRepositoryMock
import by.alexandr7035.banking.domain.features.cards.CardsRepository
import by.alexandr7035.banking.domain.features.savings.SavingsRepository
import by.alexandr7035.banking.domain.features.login.LoginRepository
import by.alexandr7035.banking.domain.features.otp.OtpRepository
import by.alexandr7035.banking.domain.features.profile.ProfileRepository
import by.alexandr7035.banking.domain.features.signup.SignUpRepository
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val dataModule = module {
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

    single<AppSettignsRepository> {
        AppRepositoryImpl(get())
    }

    single<SignUpRepository> {
        SignUpRepositoryMock(Dispatchers.IO)
    }

    single<LoginRepository> {
        LoginRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            prefs = get()
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
            context = androidApplication().applicationContext,
            cardsRepository = get()
        )
    }

    single<OtpRepository> {
        OtpRepositoryMock(
            coroutineDispatcher = Dispatchers.IO
        )
    }

    single {
        KsPrefs(androidApplication().applicationContext)
    }
}
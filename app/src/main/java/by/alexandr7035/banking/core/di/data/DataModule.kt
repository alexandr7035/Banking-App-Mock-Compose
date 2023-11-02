package by.alexandr7035.banking.core.di.data

import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import by.alexandr7035.banking.data.account.AccountRepositoryMock
import by.alexandr7035.banking.data.app.AppRepositoryImpl
import by.alexandr7035.banking.data.app.AppSettignsRepository
import by.alexandr7035.banking.data.app_lock.AppLockRepositoryImpl
import by.alexandr7035.banking.data.cards.CardsRepositoryMock
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.data.db.CacheDatabase
import by.alexandr7035.banking.data.login.LoginRepositoryMock
import by.alexandr7035.banking.data.otp.OtpRepositoryMock
import by.alexandr7035.banking.data.profile.ProfileRepositoryMock
import by.alexandr7035.banking.data.savings.SavingsRepositoryMock
import by.alexandr7035.banking.data.signup.SignUpRepositoryMock
import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.app_lock.AppLockRepository
import by.alexandr7035.banking.domain.features.cards.CardsRepository
import by.alexandr7035.banking.domain.features.login.LoginRepository
import by.alexandr7035.banking.domain.features.otp.OtpRepository
import by.alexandr7035.banking.domain.features.profile.ProfileRepository
import by.alexandr7035.banking.domain.features.savings.SavingsRepository
import by.alexandr7035.banking.domain.features.signup.SignUpRepository
import com.cioccarellia.ksprefs.KsPrefs
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
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
        SignUpRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            otpRepository = get(),
            prefs = get()
        )
    }

    single<LoginRepository> {
        LoginRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            prefs = get(),
            securedPrefs = get(named("encryptedPrefs"))
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

    single(named("encryptedPrefs")) {
        val context = androidApplication().applicationContext

        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secured_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<AppLockRepository> {
        AppLockRepositoryImpl(
            securedPreferences = get(named("encryptedPrefs")),
            context = androidApplication().applicationContext
        )
    }

    single<AccountRepository> {
        AccountRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            cardsRepository = get()
        )
    }
}
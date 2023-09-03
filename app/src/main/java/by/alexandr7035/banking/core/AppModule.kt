package by.alexandr7035.banking.core

import by.alexandr7035.banking.data.app.AppRepository
import by.alexandr7035.banking.data.app.AppRepositoryImpl
import by.alexandr7035.banking.data.login.LoginRepository
import by.alexandr7035.banking.data.login.LoginRepositoryImpl
import by.alexandr7035.banking.ui.core.AppViewModel
import by.alexandr7035.banking.ui.feature_login.LoginViewModel
import com.cioccarellia.ksprefs.KsPrefs
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }
    viewModel { AppViewModel(get()) }

    single<AppRepository> {
        AppRepositoryImpl(get())
    }

    single<LoginRepository> {
        LoginRepositoryImpl()
    }

    single {
        KsPrefs(androidApplication().applicationContext)
    }
}
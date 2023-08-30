package by.alexandr7035.banking.core

import by.alexandr7035.banking.data.login.LoginRepository
import by.alexandr7035.banking.data.login.LoginRepositoryImpl
import by.alexandr7035.banking.ui.feature_login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { LoginViewModel(get()) }

    single<LoginRepository> {
        LoginRepositoryImpl()
    }
}
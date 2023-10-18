package by.alexandr7035.banking.ui.core

import androidx.lifecycle.ViewModel
import by.alexandr7035.banking.data.app.AppRepository
import by.alexandr7035.banking.domain.usecases.login.CheckIfLoggedInUseCase
import by.alexandr7035.banking.domain.usecases.login.LogoutUseCase

class AppViewModel(
    private val appRepository: AppRepository,
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    fun isWizardViewed(): Boolean {
        return appRepository.isWizardViewed()
    }

    fun setWizardViewed() {
        appRepository.setWizardViewed(true)
    }

    fun isLoggedIn(): Boolean {
        return checkIfLoggedInUseCase.execute()
    }

    fun logOut() {
        logoutUseCase.execute()
    }
}
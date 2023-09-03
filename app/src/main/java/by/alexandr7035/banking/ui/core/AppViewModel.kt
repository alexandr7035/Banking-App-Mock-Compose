package by.alexandr7035.banking.ui.core

import androidx.lifecycle.ViewModel
import by.alexandr7035.banking.data.app.AppRepository

class AppViewModel(
    private val appRepository: AppRepository
): ViewModel() {
    fun isWizardViewed(): Boolean {
        return appRepository.isWizardViewed()
    }

    fun isLoggedIn(): Boolean {
        return appRepository.isLoggedIn()
    }

    fun onLoginCompleted(accessToken: String = "mock token") {
        appRepository.setLoggedIn(accessToken)
    }

    fun setWizardViewed() {
        appRepository.setWizardViewed(true)
    }
}
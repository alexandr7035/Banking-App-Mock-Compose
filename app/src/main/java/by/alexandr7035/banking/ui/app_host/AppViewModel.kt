package by.alexandr7035.banking.ui.app_host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.app_lock.CheckAppLockUseCase
import by.alexandr7035.banking.domain.features.login.CheckIfLoggedInUseCase
import by.alexandr7035.banking.domain.features.onboarding.CheckIfPassedOnboardingUseCase
import by.alexandr7035.banking.ui.app_host.navigation.model.ConditionalNavigation
import by.alexandr7035.banking.ui.core.error.asUiTextError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val checkIfPassedOnboardingUseCase: CheckIfPassedOnboardingUseCase,
    private val checkAppLockUseCase: CheckAppLockUseCase
) : ViewModel() {

    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(AppState.Loading)
    val appState = _appState.asStateFlow()

    // This is a global app's viewModel
    init {
        emitIntent(AppIntent.EnterApp)
    }

    fun emitIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.EnterApp -> {
                reduceAppLoading()

                viewModelScope.launch {

                    val isLoggedIn = OperationResult.runWrapped {
                        checkIfLoggedInUseCase.execute()
                    }

                    when (isLoggedIn) {
                        is OperationResult.Success -> {

                            val hasPassedOnboarding = checkIfPassedOnboardingUseCase.execute()
                            val appLocked = checkAppLockUseCase.execute()

                            reduceAppReady(
                                conditionalNavigation = ConditionalNavigation(
                                    // Require create applock if closed this step on registration
                                    requireCreateAppLock = !appLocked && isLoggedIn.data,
                                    requireLogin = !isLoggedIn.data,
                                    requireOnboarding = !hasPassedOnboarding
                                ),
                                appLocked = appLocked
                            )
                        }

                        is OperationResult.Failure -> {
                            reduceError(isLoggedIn.error.errorType)
                        }
                    }
                }
            }

            AppIntent.TryPostUnlock -> {
                val currState = _appState.value

                if (currState is AppState.Ready) {
                    _appState.update {
                        currState.copy(
                            requireUnlock = false
                        )
                    }
                }
            }
        }
    }

    private fun reduceAppLoading() {
        _appState.update {
            AppState.Loading
        }
    }

    private fun reduceAppReady(
        conditionalNavigation: ConditionalNavigation,
        appLocked: Boolean
    ) {
        _appState.value = AppState.Ready(
            conditionalNavigation = conditionalNavigation,
            requireUnlock = appLocked
        )
    }

    private fun reduceError(errorType: ErrorType) {
        _appState.value = AppState.InitFailure(errorType.asUiTextError())
    }
}
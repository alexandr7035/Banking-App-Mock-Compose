package by.alexandr7035.banking.ui.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.data.app.AppRepository
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.usecases.login.CheckIfLoggedInUseCase
import by.alexandr7035.banking.domain.usecases.login.LogoutUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val appRepository: AppRepository,
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val logoutUseCase: LogoutUseCase
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
                            val hasPassedOnboarding = appRepository.isWizardViewed()

                            reduceAppReady(
                                isLoggedIn = isLoggedIn.data,
                                hasPassedOnboarding = hasPassedOnboarding
                            )
                        }

                        is OperationResult.Failure -> {
                            reduceError(isLoggedIn.error.errorType)
                        }
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
        isLoggedIn: Boolean,
        hasPassedOnboarding: Boolean
    ) {
        _appState.value = AppState.Ready(
            isLoggedIn = isLoggedIn,
            passedOnboarding = hasPassedOnboarding
        )
    }

    private fun reduceError(errorType: ErrorType) {
        _appState.value = AppState.InitFailure(errorType.asUiTextError())
    }
}
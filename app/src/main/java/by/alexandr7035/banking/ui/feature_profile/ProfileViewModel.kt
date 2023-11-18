package by.alexandr7035.banking.ui.feature_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.login.LogoutUseCase
import by.alexandr7035.banking.domain.features.profile.GetCompactProfileUseCase
import by.alexandr7035.banking.domain.features.profile.model.CompactProfile
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_logout.LogoutIntent
import by.alexandr7035.banking.ui.feature_logout.LogoutViewModel
import by.alexandr7035.banking.ui.feature_profile.model.ProfileUi
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCompactProfileUseCase: GetCompactProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel(), LogoutViewModel {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state = _state.asStateFlow()

    private val errorHandler = CoroutineExceptionHandler { _, e ->
        reduceError(ErrorType.fromThrowable(e))
    }

    fun emitIntent(intent: ProfileScreenIntent) {
        when (intent) {
            is ProfileScreenIntent.EnterScreen -> {
                reduceProfileLoading()

                viewModelScope.launch(errorHandler) {
                    val profile = getCompactProfileUseCase.execute()
                    reduceData(profile)
                }
            }
        }
    }

    override fun emitLogoutIntent(intent: LogoutIntent) {
        when (intent) {
            is LogoutIntent.ToggleLogoutDialog -> {
                reduceLogoutDialog(showDialog = intent.isShown)
            }

            is LogoutIntent.ConfirmLogOut -> {
                reduceLogoutLoading()

                viewModelScope.launch {
                    val res = OperationResult.runWrapped {
                        logoutUseCase.execute()
                    }
                    reduceLogoutResult(res)
                }
            }
        }
    }


    private fun reduceProfileLoading() {
        _state.update { curr ->
            curr.copy(isProfileLoading = true)
        }
    }

    private fun reduceError(errorType: ErrorType) {
        _state.update { curr ->
            curr.copy(
                isProfileLoading = false,
                error = errorType.asUiTextError()
            )
        }
    }

    private fun reduceData(profile: CompactProfile) {
        _state.update { curr ->
            curr.copy(
                isProfileLoading = false,
                profile = ProfileUi.mapFromDomain(profile)
            )
        }
    }

    fun consumeLogoutEvent() {
        _state.update { curr ->
            val logoutState = curr.logoutState.copy(
                isLoading = false,
                logoutEvent = consumed()
            )
            curr.copy(logoutState = logoutState)
        }
    }

    private fun reduceLogoutDialog(showDialog: Boolean) {
        _state.update { curr ->
            val logoutState = curr.logoutState.copy(
                showLogoutDialog = showDialog
            )
            curr.copy(logoutState = logoutState)
        }
    }

    private fun reduceLogoutLoading() {
        _state.update { curr ->
            val logoutState = curr.logoutState.copy(isLoading = true)
            curr.copy(logoutState = logoutState)
        }
    }

    private fun reduceLogoutResult(result: OperationResult<Unit>) {
        _state.update { curr ->
            val logoutState = curr.logoutState.copy(
                isLoading = false,
                logoutEvent = triggered(result)
            )
            curr.copy(logoutState = logoutState)
        }
    }
}
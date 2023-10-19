package by.alexandr7035.banking.ui.feature_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.usecases.login.LogoutUseCase
import by.alexandr7035.banking.domain.usecases.profile.CompactProfile
import by.alexandr7035.banking.domain.usecases.profile.GetCompactProfileUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCompactProfileUseCase: GetCompactProfileUseCase,
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        ProfileScreenState()
    )
    val state = _state.asStateFlow()

    fun emitIntent(intent: ProfileScreenIntent) {
        when (intent) {
            is ProfileScreenIntent.EnterScreen -> {
                reduceProfileLoading()

                viewModelScope.launch {
                    val profile = OperationResult.runWrapped {
                        getCompactProfileUseCase.execute()
                    }

                    when (profile) {
                        is OperationResult.Success -> {
                            reduceData(profile.data)
                        }

                        is OperationResult.Failure -> {
                            reduceError(profile.error.errorType)
                        }
                    }
                }
            }

            is ProfileScreenIntent.ToggleLogoutDialog -> {
                reduceLogoutDialog(showDialog = intent.isShown)
            }

            is ProfileScreenIntent.ConfirmLogOut -> {
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
}
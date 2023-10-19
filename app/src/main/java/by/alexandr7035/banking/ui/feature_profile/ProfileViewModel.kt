package by.alexandr7035.banking.ui.feature_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.usecases.profile.CompactProfile
import by.alexandr7035.banking.domain.usecases.profile.GetCompactProfileUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import de.palm.composestateevents.consumed
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getCompactProfileUseCase: GetCompactProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        ProfileScreenState()
    )
    val state = _state.asStateFlow()

    fun emitIntent(intent: ProfileScreenIntent) {
        when (intent) {
            is ProfileScreenIntent.EnterScreen -> {
                reduceLoading()

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

            }

            is ProfileScreenIntent.ConfirmLogOut -> {

            }
        }
    }

    private fun reduceLoading() {
        _state.update { curr ->
            curr.copy(isLoading = true)
        }
    }

    private fun reduceError(errorType: ErrorType) {
        _state.update { curr ->
            curr.copy(
                isLoading = false,
                error = errorType.asUiTextError()
            )
        }
    }

    private fun reduceData(profile: CompactProfile) {
        _state.update { curr ->
            curr.copy(
                isLoading = false,
                profile = ProfileUi.mapFromDomain(profile)
            )
        }
    }

    fun consumeLogoutEvent() {
        _state.update { curr ->
            curr.copy(logoutEvent = consumed())
        }
    }
}
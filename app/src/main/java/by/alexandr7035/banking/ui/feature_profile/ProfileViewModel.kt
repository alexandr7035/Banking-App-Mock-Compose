package by.alexandr7035.banking.ui.feature_profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.data.profile.ProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepository) : ViewModel() {
    private val _state = MutableStateFlow(
        ProfileScreenState()
    )
    val state = _state.asStateFlow()

    fun emitIntent(intent: ProfileScreenIntent) {
        val currentState = _state.value
        reduce(intent, currentState)
    }

    private fun reduce(intent: ProfileScreenIntent, currentState: ProfileScreenState) {
        when (intent) {
            is ProfileScreenIntent.LoadScreen -> {
                viewModelScope.launch {
                    val profile = runCatching {
                        repository.getProfile()
                    }

                    if (profile.isSuccess) {
                        _state.update { curr ->
                            curr.copy(isLoading = false, profile = profile.getOrThrow())
                        }
                    }
                }
            }
        }
    }
}
package by.alexandr7035.banking.ui.feature_signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InitSignUpViewModel: ViewModel() {
    private val _state = MutableStateFlow(InitSignUpState())
    val state = _state.asStateFlow()


}

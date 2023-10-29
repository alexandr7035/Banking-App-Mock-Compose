package by.alexandr7035.banking.ui.feature_app_lock.components

import by.alexandr7035.banking.ui.core.resources.UiText

data class AppLockUiState(
    val prompt: UiText = UiText.DynamicString(""),
    val pinLength: Int = 4,
    val pinValue: String = "",
    val error: UiText? = null,
    val isLoading: Boolean = false,
    // TODO biometric btn
)
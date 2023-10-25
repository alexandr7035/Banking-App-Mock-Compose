package by.alexandr7035.banking.ui.feature_signup.confirm_signup

import by.alexandr7035.banking.domain.features.otp.model.OtpConfiguration
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_otp.OtpConfirmationState

data class ConfirmSignUpScreenState(
    val isInitialLoading: Boolean = true,
    val otpConfiguration: OtpConfiguration? = null,
    val otpState: OtpConfirmationState? = null,
    val screenError: UiText? = null,
)
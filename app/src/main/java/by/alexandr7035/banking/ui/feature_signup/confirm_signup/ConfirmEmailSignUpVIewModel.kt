package by.alexandr7035.banking.ui.feature_signup.confirm_signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.otp.RequestOtpGenerationUseCase
import by.alexandr7035.banking.domain.features.otp.model.OtpConfiguration
import by.alexandr7035.banking.domain.features.signup.ConfirmSignUpWithEmailUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import by.alexandr7035.banking.ui.feature_otp.OtpConfirmationIntent
import by.alexandr7035.banking.ui.feature_otp.OtpConfirmationState
import by.alexandr7035.banking.ui.feature_otp.OtpViewModel
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConfirmEmailSignUpVIewModel(
    private val requestOtpGenerationUseCase: RequestOtpGenerationUseCase,
    private val confirmSignUpWithEmailUseCase: ConfirmSignUpWithEmailUseCase
) : ViewModel(), OtpViewModel {

    private val _state: MutableStateFlow<ConfirmSignUpScreenState> = MutableStateFlow(
        ConfirmSignUpScreenState()
    )

    override val otpLength: Int = 4
    val otpState = _state.asStateFlow()

    override fun emitOtpIntent(intent: OtpConfirmationIntent) {
        when (intent) {
            is OtpConfirmationIntent.RequestInitialOtp -> {
                reduce(intent)
            }

            is OtpConfirmationIntent.CodeChanged -> {
                reduce(intent)
            }

            is OtpConfirmationIntent.SubmitCode -> {
                reduce(intent)
            }

            is OtpConfirmationIntent.ResendCode -> {
                reduce(intent)
            }
        }
    }

    private fun reduce(intent: OtpConfirmationIntent.RequestInitialOtp) {
        _state.update {
            it.copy(isInitialLoading = true)
        }

        val otpConfiguration = OtpConfiguration(
            otpType = intent.otpType,
            operationType = intent.otpOperationType,
            otpDestination = intent.otpDestination
        )

        viewModelScope.launch {
            val initialOtpRes = OperationResult.runWrapped {
                requestOtpGenerationUseCase.execute(otpConfiguration)
            }

            when (initialOtpRes) {
                is OperationResult.Success -> {
                    _state.update {
                        it.copy(
                            isInitialLoading = false,
                            otpConfiguration = otpConfiguration,
                            otpState = OtpConfirmationState(
                                codeSentTo = otpConfiguration.otpDestination
                            ),
                        )
                    }
                }

                is OperationResult.Failure -> {
                    _state.update {
                        it.copy(
                            isInitialLoading = false,
                            otpState = null,
                            screenError = initialOtpRes.error.errorType.asUiTextError()
                        )
                    }
                }
            }
        }
    }

    private fun reduce(intent: OtpConfirmationIntent.SubmitCode) {
        val currentState = _state.value

        if (currentState.otpConfiguration != null && currentState.otpState != null) {
            val currentOtpState = currentState.otpState

            _state.update {
                it.copy(otpState = currentOtpState.copy(isLoading = true))
            }

            viewModelScope.launch {
                val res = OperationResult.runWrapped {
                    confirmSignUpWithEmailUseCase.execute(
                        otpConfiguration = currentState.otpConfiguration,
                        otpCode = currentState.otpState.code.value
                    )
                }

                _state.update {
                    it.copy(
                        otpState = currentOtpState.copy(
                            isLoading = false,
                            code = UiField(""),
                            codeSubmittedEvent = triggered(res),
                        )
                    )
                }
            }
        }
    }

    private fun reduce(intent: OtpConfirmationIntent.ResendCode) {
        val currentState = _state.value

        if (currentState.otpConfiguration != null && currentState.otpState != null) {
            val currentOtpState = currentState.otpState

            _state.update {
                it.copy(otpState = currentOtpState.copy(isLoading = true))
            }

            viewModelScope.launch {
                val res = OperationResult.runWrapped {
                    requestOtpGenerationUseCase.execute(
                        otpConfiguration = currentState.otpConfiguration
                    )
                }

                _state.update {
                    it.copy(
                        otpState = currentOtpState.copy(
                            isLoading = false,
                            codeResentEvent = triggered(res)
                        )
                    )
                }
            }
        }
    }

    private fun reduce(
        intent: OtpConfirmationIntent.CodeChanged
    ) {
        val currentOtpState = _state.value.otpState

        _state.update {
            it.copy(
                otpState = currentOtpState?.copy(
                    code = UiField(intent.code),
                    submitBtnEnabled = intent.code.length == otpLength
                )
            )
        }
    }

    override fun consumeOtpSubmittedEvent() {
        _state.update { currentState ->
            currentState.copy(
                otpState = currentState.otpState?.copy(
                    codeSubmittedEvent = consumed()
                )
            )
        }
    }

    override fun consumeOtpResentEvent() {
        _state.update { currentState ->
            currentState.copy(
                otpState = currentState.otpState?.copy(
                    codeResentEvent = consumed()
                )
            )
        }
    }
}
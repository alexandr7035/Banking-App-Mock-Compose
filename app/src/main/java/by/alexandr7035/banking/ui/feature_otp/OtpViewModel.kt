package by.alexandr7035.banking.ui.feature_otp

// Use this VM in flows requiring OTP confirmation
// It will enforce VM to handle OTP state in user flow
interface OtpViewModel {
    val otpLength: Int

    fun emitOtpIntent(intent: OtpConfirmationIntent)

    // Both methods should be used to consume StateEvent
    fun consumeOtpSubmittedEvent()

    fun consumeOtpResentEvent()
}
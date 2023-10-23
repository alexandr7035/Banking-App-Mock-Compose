package by.alexandr7035.banking.domain.features.signup

interface SignUpRepository {
    suspend fun signUpWithEmail(payload: SignUpPayload)

    suspend fun confirmSignUp(confirmationCode: Int)
}
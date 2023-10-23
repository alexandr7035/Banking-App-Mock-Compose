package by.alexandr7035.banking.domain.features.signup

class ConfirmSignUpUseCase(
    private val signUpRepository: SignUpRepository
) {
    suspend fun execute(confirmationCode: Int) {
        return signUpRepository.confirmSignUp(confirmationCode)
    }
}
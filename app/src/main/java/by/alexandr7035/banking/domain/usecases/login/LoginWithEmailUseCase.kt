package by.alexandr7035.banking.domain.usecases.login

class LoginWithEmailUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(
        email: String,
        password: String
    ) {
        return loginRepository.loginWithEmail(email, password)
    }
}
package by.alexandr7035.banking.domain.usecases.login

import by.alexandr7035.banking.data.login.LoginRepository

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
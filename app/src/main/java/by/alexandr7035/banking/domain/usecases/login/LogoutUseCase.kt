package by.alexandr7035.banking.domain.usecases.login

import by.alexandr7035.banking.data.login.LoginRepository

class LogoutUseCase(
    private val loginRepository: LoginRepository
) {
    fun execute() {
        return loginRepository.logOut()
    }
}
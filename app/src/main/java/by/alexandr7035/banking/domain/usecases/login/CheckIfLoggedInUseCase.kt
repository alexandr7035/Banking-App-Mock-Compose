package by.alexandr7035.banking.domain.usecases.login

import by.alexandr7035.banking.data.login.LoginRepository

class CheckIfLoggedInUseCase(
    private val loginRepository: LoginRepository
) {
    fun execute(): Boolean {
        return loginRepository.checkIfLoggedIn()
    }
}
package by.alexandr7035.banking.domain.usecases.login

class LogoutUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute() {
        return loginRepository.logOut()
    }
}
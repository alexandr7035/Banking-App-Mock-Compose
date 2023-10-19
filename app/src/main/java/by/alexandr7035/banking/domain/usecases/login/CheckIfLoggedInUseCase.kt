package by.alexandr7035.banking.domain.usecases.login

class CheckIfLoggedInUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(): Boolean {
        return loginRepository.checkIfLoggedIn()
    }
}
package by.alexandr7035.banking.domain.usecases.savings

import by.alexandr7035.banking.domain.repository.savings.Saving
import by.alexandr7035.banking.domain.repository.savings.SavingsRepository

class GetSavingByIdUseCase(
    private val savingsRepository: SavingsRepository
) {
    suspend fun execute(savingId: Long): Saving {
        return savingsRepository.getSavingById(savingId)
    }
}
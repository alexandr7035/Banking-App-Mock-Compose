package by.alexandr7035.banking.domain.features.savings

import by.alexandr7035.banking.domain.features.savings.model.Saving

class GetSavingByIdUseCase(
    private val savingsRepository: SavingsRepository
) {
    suspend fun execute(savingId: Long): Saving {
        return savingsRepository.getSavingById(savingId)
    }
}
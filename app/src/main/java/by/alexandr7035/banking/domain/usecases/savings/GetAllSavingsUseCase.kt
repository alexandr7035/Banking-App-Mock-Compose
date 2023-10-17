package by.alexandr7035.banking.domain.usecases.savings

import by.alexandr7035.banking.domain.repository.savings.Saving
import by.alexandr7035.banking.domain.repository.savings.SavingsRepository

class GetAllSavingsUseCase(
    private val savingsRepository: SavingsRepository
) {
    fun execute(): List<Saving> {
        return savingsRepository.getSavings().sortedByDescending {
            it.completedPercentage
        }
    }
}
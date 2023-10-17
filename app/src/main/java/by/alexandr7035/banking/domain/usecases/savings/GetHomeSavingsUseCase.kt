package by.alexandr7035.banking.domain.usecases.savings

import by.alexandr7035.banking.domain.repository.savings.Saving
import by.alexandr7035.banking.domain.repository.savings.SavingsRepository

class GetHomeSavingsUseCase(
    private val savingsRepository: SavingsRepository
) {
    fun execute(): List<Saving> {
        val all = savingsRepository.getSavings()

        val completed = all.filter {
            it.isCompleted
        }

        val topCompletion = all.filter {
            !it.isCompleted
        }.sortedByDescending { it.completedPercentage }

        // Show "almost completed"
        // Priority to bigger completedPercentage (excluding 100%)
        return (topCompletion + completed).take(DISPLAYED_COUNT)
    }

    companion object {
        private const val DISPLAYED_COUNT = 3
    }
}
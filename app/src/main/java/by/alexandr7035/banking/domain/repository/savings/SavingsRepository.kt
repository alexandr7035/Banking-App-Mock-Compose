package by.alexandr7035.banking.domain.repository.savings

interface SavingsRepository {
    suspend fun getSavings(): List<Saving>
}
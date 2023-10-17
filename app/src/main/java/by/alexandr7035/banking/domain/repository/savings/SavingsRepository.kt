package by.alexandr7035.banking.domain.repository.savings

interface SavingsRepository {
    fun getSavings(): List<Saving>
}
package by.alexandr7035.banking.domain.features.savings

import by.alexandr7035.banking.domain.features.savings.model.Saving

interface SavingsRepository {
    suspend fun getSavings(): List<Saving>
    suspend fun getSavingById(id: Long): Saving
}
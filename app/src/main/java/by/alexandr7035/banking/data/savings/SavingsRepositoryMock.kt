package by.alexandr7035.banking.data.savings

import by.alexandr7035.banking.domain.repository.savings.Saving
import by.alexandr7035.banking.domain.repository.savings.SavingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SavingsRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher
) : SavingsRepository {
    override suspend fun getSavings(): List<Saving> = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY_MILLS)

        return@withContext listOf(
            Saving(
                title = "Buy Playstation",
                description = "Slim 1 TB 56 Games",
                completedPercentage = 0.7f,
                iconUrl = "https://svgur.com/i/y7P.svg"
            ),
            Saving(
                title = "Buy Car Remote",
                description = "Mercedez Benz 001",
                completedPercentage = 0.8f,
                iconUrl = "https://svgur.com/i/y7P.svg"
            ),
            Saving(
                title = "Buy Bicycle",
                description = "Mountain bike R7",
                completedPercentage = 0.6f,
                iconUrl = "https://svgur.com/i/y7P.svg"
            ),
            Saving(
                title = "Buy Mini Vespa",
                description = "",
                completedPercentage = 1f,
                iconUrl = "https://svgur.com/i/y7P.svg"
            ),
            Saving(
                title = "Buy Barbie Doll",
                description = "One Set Purple",
                completedPercentage = 1f,
                iconUrl = "https://svgur.com/i/y7P.svg"
            ),
        )
    }

    companion object {
        private const val MOCK_DELAY_MILLS = 700L
    }
}
package by.alexandr7035.banking.data.savings

import android.content.Context
import by.alexandr7035.banking.domain.repository.savings.Saving
import by.alexandr7035.banking.domain.repository.savings.SavingsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class SavingsRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val context: Context
) : SavingsRepository {
    override suspend fun getSavings(): List<Saving> = withContext(coroutineDispatcher) {
        delay(MOCK_DELAY_MILLS)

        return@withContext listOf(
            Saving(
                id = 0L,
                title = "Buy Playstation",
                description = "Slim 1 TB 56 Games",
                completedPercentage = 0.7f,
                iconUrl = getMockImageUrl( "ic_playstation")
            ),
            Saving(
                id = 1L,
                title = "Buy Car Remote",
                description = "Mercedez Benz 001",
                completedPercentage = 0.8f,
                iconUrl = getMockImageUrl( "ic_car")
            ),
            Saving(
                id = 2L,
                title = "Buy Bicycle",
                description = "Mountain bike R7",
                completedPercentage = 0.6f,
                iconUrl = getMockImageUrl( "ic_bike")
            ),
            Saving(
                id = 3L,
                title = "Buy Mini Vespa",
                description = "Mini Vespa Scooter 6v",
                completedPercentage = 1f,
                iconUrl = getMockImageUrl( "ic_scooter")
            ),
            Saving(
                id = 4L,
                title = "Buy Barbie Doll",
                description = "One Set Purple",
                completedPercentage = 1f,
                iconUrl = getMockImageUrl( "ic_doll")
            ),
        )
    }

    private fun getMockImageUrl(
        drawableName: String
    ): String {
        val packageName = context.packageName
        return "android.resource://$packageName/drawable/$drawableName"
    }

    companion object {
        private const val MOCK_DELAY_MILLS = 400L
    }
}
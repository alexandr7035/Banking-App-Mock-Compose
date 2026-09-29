package by.alexandr7035.banking.testsetup

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import by.alexandr7035.banking.core.di.data.dataModule
import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.cards.CardsRepository
import by.alexandr7035.banking.testdata.TestCards
import by.alexandr7035.banking.testdata.TestDatabase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.koinApplication

@RunWith(AndroidJUnit4::class)
class TestDatabaseModuleTest {

    @Test
    fun repositoriesReadPreparedCardFromTestDatabase() = runBlocking {
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext

        val database = TestDatabase.create(context)
        val testKoin = koinApplication {}

        try {
            // Given: application definitions followed by test overrides
            testKoin.modules(dataModule)
            testKoin.modules(testDatabaseModule(database))

            val card = TestCards.debitCard(balance = 250f)
            database.getCardsDao().addCard(card)

            // When: resolve the real repositories from isolated Koin
            val cardsRepository =
                testKoin.koin.get<CardsRepository>()

            val accountRepository =
                testKoin.koin.get<AccountRepository>()

            val loadedCard = cardsRepository.getCardById(card.number)
            val totalBalance = accountRepository.getBalanceFlow().first()

            // Then: both repositories read the prepared balance
            assertEquals(card.number, loadedCard.cardId)
            assertEquals(250f, loadedCard.recentBalance.value, 0f)
            assertEquals(250f, totalBalance.value, 0f)
        } finally {
            testKoin.close()
            database.close()
        }
    }
}
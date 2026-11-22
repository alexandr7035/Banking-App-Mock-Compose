package by.alexandr7035.banking.testdata

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import by.alexandr7035.banking.data.db.CacheDatabase
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TestDatabaseSetupTest {

    private lateinit var database: CacheDatabase

    @Before
    fun setUp() {
        val context =
            InstrumentationRegistry.getInstrumentation().targetContext
        database = TestDatabase.create(context)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun newDatabaseIsEmptyAndStoresPreparedCard() = runBlocking {
        // Given
        val cardsDao = database.getCardsDao()
        assertTrue(cardsDao.getCards().isEmpty())

        val expectedCard = TestCards.debitCard(balance = 250f)

        // When
        cardsDao.addCard(expectedCard)

        // Then
        val cards = cardsDao.getCards()
        assertEquals(1, cards.size)
        assertEquals(expectedCard, cards.single())
    }
}

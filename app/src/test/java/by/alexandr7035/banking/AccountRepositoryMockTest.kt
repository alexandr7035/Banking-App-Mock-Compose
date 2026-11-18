package by.alexandr7035.banking

import by.alexandr7035.banking.data.account.AccountRepositoryMock
import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.cards.model.CardType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class AccountRepositoryMockTest {

    @Test
    fun sendFromCard_reducesBalance_whenFundsAreSufficient() = runBlocking {
        // Given: a card with a balance of 250
        val balance = 250f

        val card = CardEntity(
            number = "5555555555554444",
            isPrimary = true,
            cardType = CardType.DEBIT,
            recentBalance = balance,
            cardHolder = "Test User",
            expiration = 1893456000000L, // 2030-01-01 UTC
            addressFirstLine = "Test Street 1",
            addressSecondLine = "Test City",
            addedDate = 1704067200000L, // 2024-01-01 UTC
        )

        val fakeCardsDao = FakeCardsDao(listOf(card))

        val accountRepositoryMock = AccountRepositoryMock(
            Dispatchers.Unconfined,
            fakeCardsDao
        )

        // When: sending 100
        accountRepositoryMock.sendFromCard(
            card.number,
            MoneyAmount(100f),
            1L

        )
        // Then: the saved balance is 150
        val updatedCard = fakeCardsDao.getCardByNumber(card.number)

        assertEquals(150f, updatedCard!!.recentBalance, 0.001f)

    }
}
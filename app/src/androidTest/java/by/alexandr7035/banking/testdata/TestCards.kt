package by.alexandr7035.banking.testdata

import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.domain.features.cards.model.CardType
    object TestCards {
        fun debitCard(balance: Float = 250f): CardEntity = CardEntity(
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
    }

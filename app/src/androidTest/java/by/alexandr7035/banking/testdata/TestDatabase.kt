package by.alexandr7035.banking.testdata

import android.content.Context
import androidx.room.Room
import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.db.CacheDatabase
import by.alexandr7035.banking.data.db.convertors.MoneyAmountConvertor
import by.alexandr7035.banking.domain.features.cards.model.CardType

object TestDatabase {

    fun create(context: Context): CacheDatabase =
        Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            CacheDatabase::class.java
        )
            .addTypeConverter(MoneyAmountConvertor())
            .build()
}

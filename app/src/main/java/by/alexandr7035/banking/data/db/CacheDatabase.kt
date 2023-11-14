package by.alexandr7035.banking.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.data.db.convertors.MoneyAmountConvertor
import by.alexandr7035.banking.data.transactions.db.TransactionDao
import by.alexandr7035.banking.data.transactions.db.TransactionEntity

@Database(entities = [CardEntity::class, TransactionEntity::class], version = 1)
@TypeConverters(MoneyAmountConvertor::class)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun getCardsDao(): CardsDao
    abstract fun getTransactionsDao(): TransactionDao
}

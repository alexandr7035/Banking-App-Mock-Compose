package by.alexandr7035.banking.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import by.alexandr7035.banking.data.cards.cache.CardEntity
import by.alexandr7035.banking.data.cards.cache.CardsDao

@Database(entities = [CardEntity::class], version = 1)
abstract class CacheDatabase: RoomDatabase() {
    abstract fun getCardsDao(): CardsDao
}

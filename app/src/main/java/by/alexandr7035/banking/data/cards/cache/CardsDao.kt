package by.alexandr7035.banking.data.cards.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CardsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCard(card: CardEntity)

    @Query("SELECT * FROM cards_cache ORDER by number")
    suspend fun getCards(): List<CardEntity>

    @Query("SELECT * FROM cards_cache WHERE number = (:number)")
    suspend fun getCardByNumber(number: String): CardEntity?
}
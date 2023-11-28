package by.alexandr7035.banking.data.cards.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CardsDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCard(card: CardEntity)

    @Query("SELECT * FROM cards_cache ORDER by number")
    suspend fun getCards(): List<CardEntity>

    @Query("SELECT * FROM cards_cache WHERE number = (:number)")
    suspend fun getCardByNumber(number: String): CardEntity?

    @Delete
    suspend fun deleteCard(card: CardEntity)

    @Update
    suspend fun updateCard(card: CardEntity)

    @Query("UPDATE cards_cache SET isPrimary = CASE WHEN number = :cardId THEN 1 ELSE 0 END")
    suspend fun markCardAsPrimary(cardId: String)

    @Query("UPDATE cards_cache SET isPrimary = 0 WHERE number = :cardId")
    suspend fun unmarkCardAsPrimary(cardId: String)
}
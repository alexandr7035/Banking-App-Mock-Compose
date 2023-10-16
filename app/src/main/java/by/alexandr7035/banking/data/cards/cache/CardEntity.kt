package by.alexandr7035.banking.data.cards.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards_cache")
data class CardEntity(
    @PrimaryKey(autoGenerate = false)
    val number: String,
    val cardHolder: String,
    val expiration: Long,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val addedDate: Long,
)

package by.alexandr7035.banking.data.transactions.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TransactionType,
    val value: MoneyAmount,
    val recentStatus: TransactionStatus,
    val cardId: String,
    val linkedContactId: String? = null,
    val createdDate: Long,
    val updatedStatusDate: Long,
)

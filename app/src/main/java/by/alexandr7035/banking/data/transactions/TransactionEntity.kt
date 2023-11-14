package by.alexandr7035.banking.data.transactions

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val type: TransactionType,
    val value: MoneyAmount,
)

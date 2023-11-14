package by.alexandr7035.banking.data.transactions.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER by id")
    suspend fun getTransactionFromCache(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE type = :filterType ORDER by id")
    suspend fun getTransactionFromCacheByType(filterType: TransactionType): List<TransactionEntity>

    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT :startPosition, :loadSize")
    suspend fun getTransactionFromCacheWithPagination(startPosition: Int, loadSize: Int): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE type = :filterType ORDER BY id DESC LIMIT :startPosition, :loadSize")
    suspend fun getTransactionFromCacheByTypeWithPagination(filterType: TransactionType, startPosition: Int, loadSize: Int): List<TransactionEntity>

    @Insert
    suspend fun addTransaction(transactionEntity: TransactionEntity)
}
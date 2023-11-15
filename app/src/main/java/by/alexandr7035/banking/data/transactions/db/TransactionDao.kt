package by.alexandr7035.banking.data.transactions.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions ORDER by id")
    suspend fun getTransactionsFromCache(): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE id = (:id)")
    suspend fun getTransaction(id: Long): TransactionEntity?

    @Update
    suspend fun updateTransaction(card: TransactionEntity)

    @Query("SELECT * FROM transactions WHERE type = :filterType ORDER by id")
    suspend fun getTransactionFromCacheByType(filterType: TransactionType): List<TransactionEntity>

    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT :startPosition, :loadSize")
    suspend fun getTransactionFromCacheWithPagination(startPosition: Int, loadSize: Int): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE type = :filterType ORDER BY id DESC LIMIT :startPosition, :loadSize")
    suspend fun getTransactionFromCacheByTypeWithPagination(filterType: TransactionType, startPosition: Int, loadSize: Int): List<TransactionEntity>

    @Insert
    suspend fun addTransaction(transactionEntity: TransactionEntity): Long
}
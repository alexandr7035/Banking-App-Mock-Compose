package by.alexandr7035.banking.data.transactions

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import by.alexandr7035.banking.data.transactions.db.TransactionDao
import by.alexandr7035.banking.data.transactions.db.TransactionEntity
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.contacts.Contact
import by.alexandr7035.banking.domain.features.contacts.ContactsRepository
import by.alexandr7035.banking.domain.features.transactions.TransactionRepository
import by.alexandr7035.banking.domain.features.transactions.model.Transaction
import by.alexandr7035.banking.domain.features.transactions.model.TransactionRowPayload
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TransactionRepositoryMock(
    private val workManager: WorkManager,
    private val transactionDao: TransactionDao,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val contactsRepository: ContactsRepository
) : TransactionRepository {
    override suspend fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>> {
        val contacts = contactsRepository.getContacts()

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_MAX_SIZE,
                initialLoadSize = PAGE_MAX_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                TransactionSource(
                    filterByType = filterByType,
                    transactionDao = transactionDao
                )
            }
        ).flow.map {
            it.map { cachedTx ->
                mapTransactionFromCache(cachedTx, contacts)
            }
        }
    }

    override fun getTransactionStatusFlow(transactionId: Long): Flow<TransactionStatus> {
        return flow {
            // Emit last cached status
            while (true) {
                val tx = transactionDao.getTransaction(transactionId) ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)
                emit(tx.recentStatus)

                delay(MOCK_TRANSACTION_STATUS_CHECK_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun submitTransaction(payload: TransactionRowPayload) {
        val raw = TransactionEntity(
            type = payload.type,
            value = payload.amount,
            linkedContactId = payload.contactId,
            createdDate = System.currentTimeMillis(),
            recentStatus = TransactionStatus.PENDING,
            updatedStatusDate = System.currentTimeMillis(),
            cardId = payload.cardId
        )
        val savedId = transactionDao.addTransaction(raw)

        val data = Data.Builder()
            .putLong(TransactionWorker.TRANSACTION_ID_KEY, savedId)
            .build()

        val workRequest =
            OneTimeWorkRequestBuilder<TransactionWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresBatteryNotLow(false)
                        .build()
                )
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setInputData(data)
                .build()

        workManager.enqueue(workRequest)
    }

    private fun mapTransactionFromCache(
        entity: TransactionEntity,
        contacts: List<Contact>
    ): Transaction {
        return Transaction(
            id = entity.id,
            type = entity.type,
            value = entity.value,
            recentStatus = entity.recentStatus,
            linkedContact = when (entity.type) {
                TransactionType.TOP_UP -> null
                else -> entity.linkedContactId?.let { id -> contacts.find { contact -> contact.id == id } }
            },
            createdDate = entity.createdDate,
            updatedStatusDate = entity.updatedStatusDate
        )
    }

    companion object {
        private const val PAGE_MAX_SIZE = 30
        private const val PREFETCH_DISTANCE = 5
        private const val MOCK_TRANSACTION_STATUS_CHECK_DELAY = 5000L
    }
}
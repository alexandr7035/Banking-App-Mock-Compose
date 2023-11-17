package by.alexandr7035.banking.data.transactions

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import by.alexandr7035.banking.data.transactions.db.TransactionDao
import by.alexandr7035.banking.data.transactions.db.TransactionEntity
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.core.notifications.TransactionNotificationHelper
import kotlinx.coroutines.delay

class TransactionWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val transactionDao: TransactionDao,
    private val accountRepository: AccountRepository,
    private val transactionNotificationHelper: TransactionNotificationHelper
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TRANSACTION_ID_KEY = "transaction_id"
        const val MOCK_TRANSACTION_DELAY = 10000L
    }

    override suspend fun doWork(): Result {
      try {
            val transactionId = inputData.getLong(TRANSACTION_ID_KEY, -1)
            Log.d("WORKER_TAG", "Run transaction operation for transaction: $transactionId")

            if (transactionId != -1L) {
                delay(MOCK_TRANSACTION_DELAY)

                val transaction = transactionDao.getTransaction(transactionId) ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)
                val transactionResult = executeTransaction(transaction = transaction)

                return when (transactionResult) {
                    is OperationResult.Success -> {
                        transactionNotificationHelper.apply {
                            val notificationUi = successMessage(
                                transactionType = transaction.type,
                                cardId = transaction.cardId,
                                amount = transaction.value
                            )

                            showNotification(notificationUi)
                        }

                        Result.success()
                    }
                    is OperationResult.Failure -> {
                        transactionNotificationHelper.apply {
                            val notificationUi = errorMessage(transactionResult.error)
                            showNotification(notificationUi)
                        }

                        Result.failure()
                    }
                }
            } else {
                return Result.failure()
            }
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    // When set expedited, work will round in ForegroundService bellow Android 12
    // So here are notification params
    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notification = transactionNotificationHelper.getNotification(
            notificationUi = transactionNotificationHelper.pending()
        )

        return ForegroundInfo(
            System.currentTimeMillis().toInt(),
            notification
        )
    }

    private suspend fun executeTransaction(transaction: TransactionEntity): OperationResult<Unit> {
        val transactionResult = when (transaction.type) {
            TransactionType.SEND -> {
                OperationResult.runWrapped {
                    accountRepository.sendFromCard(
                        cardId = transaction.cardId,
                        amount = transaction.value,
                        contactId = transaction.linkedContactId ?: error("No contact id in SEND transaction ${transaction.id}")
                    )
                }
            }

            TransactionType.RECEIVE -> TODO()

            TransactionType.TOP_UP -> {
                OperationResult.runWrapped {
                    accountRepository.topUpCard(
                        cardId = transaction.cardId,
                        amount = transaction.value
                    )
                }
            }
        }

        when (transactionResult) {
            is OperationResult.Failure -> {
                transactionDao.updateTransaction(
                    transaction.copy(recentStatus = TransactionStatus.FAILED)
                )
            }

            is OperationResult.Success -> {
                transactionDao.updateTransaction(
                    transaction.copy(recentStatus = TransactionStatus.COMPLETED)
                )
            }
        }

        return transactionResult
    }
}
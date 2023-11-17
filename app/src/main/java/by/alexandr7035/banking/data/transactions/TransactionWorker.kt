package by.alexandr7035.banking.data.transactions

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import by.alexandr7035.banking.MainActivity
import by.alexandr7035.banking.R
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
        return try {
            val transactionId = inputData.getLong(TRANSACTION_ID_KEY, -1)
            Log.d("WORKER_TAG", "Run transaction operation for transaction: $transactionId")

            if (transactionId != -1L) {
                delay(MOCK_TRANSACTION_DELAY)

                val transaction = transactionDao.getTransaction(transactionId) ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)
                executeTransaction(transaction = transaction)

                transactionNotificationHelper.apply {
                    val notificationUi = successMessage(
                        transactionType = transaction.type,
                        cardId = transaction.cardId,
                        amount = transaction.value
                    )

                    showNotification(notificationUi)
                }

                Result.success()
            } else {
                Result.failure()
            }
        } catch (e: Exception) {

            transactionNotificationHelper.apply {
                val notificationUi = errorMessage(e)
                showNotification(notificationUi)
            }

            Result.failure()
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

    private suspend fun executeTransaction(transaction: TransactionEntity) {
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

        reduceExecutedTransaction(
            transaction = transaction,
            operationResult = transactionResult
        )
    }

    private suspend fun <T> reduceExecutedTransaction(
        transaction: TransactionEntity,
        operationResult: OperationResult<T>
    ) {
        when (operationResult) {
            is OperationResult.Failure -> {
                transactionDao.updateTransaction(
                    transaction.copy(recentStatus = TransactionStatus.FAILED)
                )

                throw operationResult.error
            }

            is OperationResult.Success -> {
                transactionDao.updateTransaction(
                    transaction.copy(recentStatus = TransactionStatus.COMPLETED)
                )
            }
        }
    }
}
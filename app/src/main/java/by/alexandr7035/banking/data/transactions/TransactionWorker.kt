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
import by.alexandr7035.banking.domain.features.account.AccountRepository
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.core.notifications.TransactionNotificationHelper
import kotlinx.coroutines.delay

class TransactionWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val transactionDao: TransactionDao,
    private val accountRepository: AccountRepository
): CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TRANSACTION_ID_KEY = "transaction_id"
        const val MOCK_TRANSACTION_DELAY = 10000L
    }

    override suspend fun doWork(): Result {
        Log.d("WORKER_TAG", "Test test ")
        return try {
            val transactionId = inputData.getLong(TRANSACTION_ID_KEY, -1)
            Log.d("WORKER_TAG", "Transaction operation for transaction: $transactionId")

            if (transactionId != -1L) {
                delay(MOCK_TRANSACTION_DELAY)

                val transaction = transactionDao.getTransaction(transactionId) ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)
                executeTransaction(transaction = transaction)

                transactionDao.updateTransaction(transaction.copy(
                    recentStatus = TransactionStatus.COMPLETED,
                    updatedStatusDate = System.currentTimeMillis()
                ))

                val notificationUi = TransactionNotificationHelper.successMessage(
                    context = applicationContext,
                    transactionType = transaction.type,
                    cardId = transaction.cardId,
                    amount = transaction.value
                )

                showNotification(
                    title = notificationUi.title,
                    message = notificationUi.message
                )

                Result.success()
            } else {
                Result.failure()
            }
        }
        catch (e: Exception) {
            val notificationUi = TransactionNotificationHelper.errorMessage(
                context = applicationContext,
                error = e
            )

            showNotification(
                title = notificationUi.title,
                message = notificationUi.message
            )

            Result.failure()
        }
    }

    // When set expedited, work will round in ForegroundService bellow Android 12
    // So here are notification params
    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notificationUi = TransactionNotificationHelper.pending()

        return ForegroundInfo(
            System.currentTimeMillis().toInt(),
            getNotification(
                title = notificationUi.title,
                message = notificationUi.message
            )
        )
    }

    private suspend fun executeTransaction(transaction: TransactionEntity) {
        when (transaction.type) {
            TransactionType.SEND -> TODO()
            TransactionType.RECEIVE -> TODO()
            TransactionType.TOP_UP -> {
                accountRepository.topUpCard(
                    cardId = transaction.cardId,
                    amount = transaction.value
                )
            }
        }
    }

    private fun showNotification(
        title: String,
        message: String
    ) {
        val notificationId = System.currentTimeMillis().toInt()
        val notification = getNotification(title, message)

        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)
        }
    }

    private fun getNotification(title: String, message: String): Notification {
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        val mutabilityFlag = PendingIntent.FLAG_IMMUTABLE

        val pendingIntent = PendingIntent.getActivity(applicationContext, 0, notificationIntent, mutabilityFlag)

        return NotificationCompat.Builder(applicationContext,
            applicationContext.getString(R.string.NOTIFICATION_CHANNEL_ID))
            .setContentTitle(title)
            .setContentText(message)
                // FIXME
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            // Make not dismissible
//            .setOngoing(true)
            // Show notification immediately (prevent 10 sec delay)
//            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }
}
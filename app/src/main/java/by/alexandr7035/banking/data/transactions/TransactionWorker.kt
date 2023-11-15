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
import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.transactions.model.TransactionStatus
import by.alexandr7035.banking.ui.core.error.asUiTextError
import kotlinx.coroutines.delay

class TransactionWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val transactionDao: TransactionDao
): CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        Log.d("WORKER_TAG", "Test test ")
        return try {
            val transactionId = inputData.getLong(TRANSACTION_ID_KEY, -1)
            Log.d("WORKER_TAG", "Transaction ${transactionId}")

            if (transactionId != -1L) {
                delay(5000)

                val transaction = transactionDao.getTransaction(transactionId) ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)
//                val transaction = transactionDao.getTransactionsFromCache()
//                Log.d("WORKER_TAG", "TransactionEnt ${transaction}")
//
                // Simulate processing delay
                transactionDao.updateTransaction(transaction.copy(
                    recentStatus = TransactionStatus.COMPLETED,
                    updatedStatusDate = System.currentTimeMillis()
                ))

                showNotification(
                    title = "Transaction submitted",
                    message = "Type ${transaction.type} value ${transaction.value}"
                )
                Result.success()
            } else {
                Result.failure()
            }
        }
        catch (e: Exception) {
            showNotification(
                title = "Transaction failed",
                message = ErrorType.fromThrowable(e).asUiTextError().asString(applicationContext)
            )
            Result.failure()
        }
    }

    companion object {
        const val TRANSACTION_ID_KEY = "transaction_id"
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
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentIntent(pendingIntent)
            // Make not dismissible
//            .setOngoing(true)
            // Show notification immediately (prevent 10 sec delay)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)
            .build()
    }

    // When set expedited, work will round in ForegroundService bellow Android 12
    // So here are notification params
    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(
            0,
            getNotification("Operation in progress", "Please, wait a minute..."),
        )
    }
}
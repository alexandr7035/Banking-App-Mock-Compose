package by.alexandr7035.banking.core

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import by.alexandr7035.banking.R
import by.alexandr7035.banking.core.di.appModule
import by.alexandr7035.banking.data.transactions.TransactionWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin

class BankingApp: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BankingApp)
            workManagerFactory()
            modules(appModule)
        }

        setupNotifications()
    }

    private fun setupNotifications() {
        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            val name = getString(R.string.TRANSACTIONS_NOTIFICATION_CHANNEL_NAME)
            val id = getString(R.string.TRANSACTIONS_NOTIFICATION_CHANNEL_ID)

            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(id, name, importance)
            channel.setSound(null, null)

            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
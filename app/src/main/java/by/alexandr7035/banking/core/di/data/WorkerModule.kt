package by.alexandr7035.banking.core.di.data

import androidx.work.WorkManager
import by.alexandr7035.banking.data.transactions.TransactionWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    single {
        WorkManager.getInstance(androidApplication().applicationContext)
    }

    worker {
        TransactionWorker(
            appContext = get(),
            workerParams = get(),
            transactionDao = get(),
            accountRepository = get(),
            transactionNotificationHelper = get()
        )
    }
}
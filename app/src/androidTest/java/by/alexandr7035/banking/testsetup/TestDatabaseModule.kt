package by.alexandr7035.banking.testsetup

import by.alexandr7035.banking.data.cards.cache.CardsDao
import by.alexandr7035.banking.data.db.CacheDatabase
import by.alexandr7035.banking.data.transactions.db.TransactionDao
import org.koin.dsl.module

fun testDatabaseModule(database: CacheDatabase) = module {

    single<CacheDatabase> { database }

    single<CardsDao> {
        get<CacheDatabase>().getCardsDao()
    }

    single<TransactionDao> {
        get<CacheDatabase>().getTransactionsDao()
    }
}
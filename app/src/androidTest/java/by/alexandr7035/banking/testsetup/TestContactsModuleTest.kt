package by.alexandr7035.banking.testsetup

import androidx.test.ext.junit.runners.AndroidJUnit4
import by.alexandr7035.banking.core.di.data.dataModule
import by.alexandr7035.banking.domain.features.contacts.ContactsRepository
import by.alexandr7035.banking.domain.features.contacts.GetRecentContactUseCase
import by.alexandr7035.banking.testdata.TestContacts
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.koinApplication

@RunWith(AndroidJUnit4::class)
class TestContactsModuleTest {

    @Test
    fun recentContactUsesPreparedRecipient() = runBlocking {
        // Given: test data replaces the production contacts repository
        val expectedContact = TestContacts.andi()
        val testKoin = koinApplication {}

        try {
            testKoin.modules(dataModule)
            testKoin.modules(testContactsModule(expectedContact))

            val repository = testKoin.koin.get<ContactsRepository>()
            val useCase = GetRecentContactUseCase(repository)

            // When: the transfer flow requests its initial recipient
            val actualContact = useCase.execute()

            // Then: the prepared recipient is returned
            assertEquals(listOf(expectedContact), repository.getContacts())
            assertEquals(expectedContact, actualContact)
        } finally {
            testKoin.close()
        }
    }
}

package by.alexandr7035.banking.data.profile

import by.alexandr7035.banking.domain.core.AppError
import by.alexandr7035.banking.domain.core.ErrorType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProfileRepositoryMock(private val dispatcher: CoroutineDispatcher) : ProfileRepository {
    override suspend fun getProfile(): Profile = withContext(dispatcher) {
        delay(300)
        Profile.mock()
    }
}
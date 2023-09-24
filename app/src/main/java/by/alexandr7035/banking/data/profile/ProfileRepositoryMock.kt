package by.alexandr7035.banking.data.profile

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProfileRepositoryMock(private val dispatcher: CoroutineDispatcher) : ProfileRepository {
    override suspend fun getProfile(): Result<Profile> = withContext(dispatcher) {
        delay(500)

        return@withContext Result.success(
            Profile.mock()
        )
    }
}
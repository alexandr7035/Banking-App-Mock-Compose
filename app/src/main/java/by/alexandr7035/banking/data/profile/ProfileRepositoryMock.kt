package by.alexandr7035.banking.data.profile

import by.alexandr7035.banking.domain.features.profile.model.CompactProfile
import by.alexandr7035.banking.domain.features.profile.ProfileRepository
import by.alexandr7035.banking.domain.features.profile.model.ProfileTier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProfileRepositoryMock(
    private val dispatcher: CoroutineDispatcher
) : ProfileRepository {
    override suspend fun getCompactProfile(): CompactProfile = withContext(dispatcher) {
        delay(MOCK_DELAY)

        return@withContext CompactProfile(
            id = "089621027821",
            firstName = "Alexander",
            lastName = "Michael",
            nickName = "@alexandermichael",
            email = "test@example.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey",
            tier = ProfileTier.BASIC,
        )
    }

    companion object {
        private const val MOCK_DELAY = 300L
    }
}
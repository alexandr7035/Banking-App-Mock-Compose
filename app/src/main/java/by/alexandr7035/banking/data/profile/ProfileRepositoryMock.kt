package by.alexandr7035.banking.data.profile

import by.alexandr7035.banking.domain.usecases.profile.CompactProfile
import by.alexandr7035.banking.domain.usecases.profile.ProfileRepository
import by.alexandr7035.banking.domain.usecases.profile.ProfileTier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class ProfileRepositoryMock(
    private val dispatcher: CoroutineDispatcher
) : ProfileRepository {
//    private val balanceStateFlow = MutableStateFlow(MOCK_LAST_BALANCE_CACHED)

    override suspend fun getCompactProfile(): CompactProfile = withContext(dispatcher) {
        delay(MOCK_DELAY)

//        startBalanceObserving()

        return@withContext CompactProfile(
            id = "089621027821",
            firstName = "Alexander",
            lastName = "Michael",
            email = "test@example.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey",
            tier = ProfileTier.BASIC,
//            balanceFlow = balanceStateFlow
        )
    }

//    private fun startBalanceObserving() {
//        flow {
//            // Check for active observers
//            while (balanceStateFlow.replayCache.isNotEmpty()) {
//                emit(Random.nextFloat() * 5000)
//                delay(BALANCE_CHECK_MOCK_DELAY)
//            }
//        }
//            .flowOn(dispatcher)
//    }

    companion object {
        private const val MOCK_DELAY = 300L
//        private const val BALANCE_CHECK_MOCK_DELAY = 5000L
//        private const val MOCK_LAST_BALANCE_CACHED = 2000F
    }
}
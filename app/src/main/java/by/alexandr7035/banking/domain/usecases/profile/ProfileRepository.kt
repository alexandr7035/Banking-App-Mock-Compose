package by.alexandr7035.banking.domain.usecases.profile

import by.alexandr7035.banking.data.profile.Profile

interface ProfileRepository {
    suspend fun getCompactProfile(): CompactProfile
}
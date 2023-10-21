package by.alexandr7035.banking.domain.features.profile

import by.alexandr7035.banking.domain.features.profile.model.CompactProfile

interface ProfileRepository {
    suspend fun getCompactProfile(): CompactProfile
}
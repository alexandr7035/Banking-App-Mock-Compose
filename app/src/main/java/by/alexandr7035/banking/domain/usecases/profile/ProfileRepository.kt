package by.alexandr7035.banking.domain.usecases.profile

interface ProfileRepository {
    suspend fun getCompactProfile(): CompactProfile
}
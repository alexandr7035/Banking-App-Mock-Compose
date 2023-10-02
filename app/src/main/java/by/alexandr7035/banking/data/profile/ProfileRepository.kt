package by.alexandr7035.banking.data.profile

interface ProfileRepository {
    suspend fun getProfile(): Profile
}
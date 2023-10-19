package by.alexandr7035.banking.domain.usecases.profile

class GetCompactProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(): CompactProfile {
        return profileRepository.getCompactProfile()
    }
}
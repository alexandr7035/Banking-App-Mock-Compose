package by.alexandr7035.banking.domain.features.profile

import by.alexandr7035.banking.domain.features.profile.model.CompactProfile

class GetCompactProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(): CompactProfile {
        return profileRepository.getCompactProfile()
    }
}
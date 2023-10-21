package by.alexandr7035.banking.domain.features.onboarding

import by.alexandr7035.banking.data.app.AppSettignsRepository

class CheckIfPassedOnboardingUseCase(
    private val settignsRepository: AppSettignsRepository
) {
    fun execute(): Boolean {
        return settignsRepository.isOnboardingPassed()
    }
}
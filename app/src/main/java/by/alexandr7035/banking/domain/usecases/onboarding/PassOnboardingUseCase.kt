package by.alexandr7035.banking.domain.usecases.onboarding

import by.alexandr7035.banking.data.app.AppSettignsRepository

class PassOnboardingUseCase(
    private val settignsRepository: AppSettignsRepository
) {
    fun execute() {
        settignsRepository.setOnboardingPassed(viewed = true)
    }
}
package by.alexandr7035.banking.data.app

interface AppSettignsRepository {
    fun setOnboardingPassed(viewed: Boolean)

    fun isOnboardingPassed(): Boolean
}
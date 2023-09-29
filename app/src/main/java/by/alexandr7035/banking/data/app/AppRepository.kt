package by.alexandr7035.banking.data.app

interface AppRepository {
    fun setWizardViewed(viewed: Boolean)

    fun isWizardViewed(): Boolean

    fun setLoggedIn(token: String)

    fun isLoggedIn(): Boolean

    fun logOut()
}
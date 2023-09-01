package by.alexandr7035.banking.data.app

import com.cioccarellia.ksprefs.KsPrefs

class AppRepositoryImpl(
    private val prefs: KsPrefs
) : AppRepository {
    override fun setWizardViewed(viewed: Boolean) {
        prefs.push(PrefKeys.IS_WIZARD_VIEWED.name, viewed)
    }

    override fun isWizardViewed(): Boolean {
        return prefs.pull(PrefKeys.IS_WIZARD_VIEWED.name, false)
    }

    override fun setLoggedIn(token: String) {
        // Token not saved in mock app
        prefs.push(PrefKeys.IS_LOGGED_IN.name, true)
    }

    override fun isLoggedIn(): Boolean {
        return prefs.pull(PrefKeys.IS_LOGGED_IN.name, false)
    }
}
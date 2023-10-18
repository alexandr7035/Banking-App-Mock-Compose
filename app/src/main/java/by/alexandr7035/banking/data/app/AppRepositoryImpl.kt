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
}
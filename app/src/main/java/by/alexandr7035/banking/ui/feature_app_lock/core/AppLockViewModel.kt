package by.alexandr7035.banking.ui.feature_app_lock.core

import by.alexandr7035.banking.ui.feature_app_lock.AppLockIntent

interface AppLockViewModel {
    val pinLength: Int

    fun emitAppLockIntent(intent: AppLockIntent)
}
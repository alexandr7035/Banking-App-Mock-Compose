package by.alexandr7035.banking.ui.feature_app_lock.core

import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsIntent

interface BiometricsViewModel {
    fun emitBiometricsIntent(intent: BiometricsIntent)
}
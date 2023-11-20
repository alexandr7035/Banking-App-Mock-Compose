package by.alexandr7035.banking.domain.features.qr_codes

import by.alexandr7035.banking.domain.features.qr_codes.model.QrPurpose
import kotlinx.coroutines.delay

class GenerateQrCodeUseCase {
    suspend fun execute(qrPurpose: QrPurpose): String {
        delay(300L)
        return when (qrPurpose) {
            // Handle in repositories later
            QrPurpose.PROFILE_CONNECTION -> "bankingapp:connect:userId"
        }
    }
}
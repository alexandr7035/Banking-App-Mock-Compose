package by.alexandr7035.banking.ui.feature_qr_codes

import by.alexandr7035.banking.ui.core.resources.UiText

data class DisplayQrState(
    val isLoading: Boolean = false,
    val qrString: String? = null,
    val error: UiText? = null,
)

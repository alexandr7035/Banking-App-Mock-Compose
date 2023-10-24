package by.alexandr7035.banking.ui.feature_2fa_confirmation

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.feature_cards.screen_add_card.UiField
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class OtpConfirmationState(
    val isLoading: Boolean = false,
    val code: UiField = UiField(""),
    val codeSentTo: String = "",
    val codeSubmittedEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
)

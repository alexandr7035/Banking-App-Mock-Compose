package by.alexandr7035.banking.ui.feature_contacts.scanned_contact

import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class ScannedContactScreenState(
    val isContactLoading: Boolean = true,
    val isLoading: Boolean = false,
    val contact: ContactUi? = null,
    val error: UiText? = null,
    val addContactResultEvent: StateEvent = consumed
)

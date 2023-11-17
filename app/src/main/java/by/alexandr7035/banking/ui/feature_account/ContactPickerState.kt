package by.alexandr7035.banking.ui.feature_account

import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed

data class ContactPickerState(
    val isLoading: Boolean = false,
    val showContactPicker: Boolean = false,
    val selectedContact: ContactUi? = null,
    val contactSelectedErrorEvent: StateEventWithContent<ErrorType> = consumed()
)

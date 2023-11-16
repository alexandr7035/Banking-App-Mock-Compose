package by.alexandr7035.banking.ui.feature_contacts.dialog_contact_picker

import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi

data class ContactPickerDialogState(
    val isLoading: Boolean = false,
    val contacts: List<ContactUi>? = null,
    val selectedContactId: Long? = null,
    val error: UiText? = null,
)

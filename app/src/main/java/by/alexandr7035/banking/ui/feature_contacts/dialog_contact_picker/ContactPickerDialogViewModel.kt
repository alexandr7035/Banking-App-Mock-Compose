package by.alexandr7035.banking.ui.feature_contacts.dialog_contact_picker

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.contacts.GetContactsUseCase
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactPickerDialogViewModel(
    private val getContactsUseCase: GetContactsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(ContactPickerDialogState())
    val state = _state.asStateFlow()

    fun emitIntent(intent: ContactPickerDialogIntent) {
        when (intent) {
            is ContactPickerDialogIntent.LoadContacts -> {
                _state.update {
                    it.copy(isLoading = true)
                }

                viewModelScope.launch {
                    val contacts = OperationResult.runWrapped {
                        getContactsUseCase.execute()
                    }

                    when (contacts) {
                        is OperationResult.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    contacts = contacts.data.map { card ->
                                        ContactUi.mapFromDomain(card)
                                    },
                                    selectedContactId = intent.defaultSelectedContactId
                                )
                            }
                        }

                        is OperationResult.Failure -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    contacts = null,
                                    error = contacts.error.errorType.asUiTextError()
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}
package by.alexandr7035.banking.ui.feature_profile

import by.alexandr7035.banking.data.profile.Profile
import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed

data class ProfileScreenState(
    val profile: Profile? = null,
    val isLoading: Boolean = true,
    val error: Throwable? = null,
)

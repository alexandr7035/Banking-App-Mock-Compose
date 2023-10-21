package by.alexandr7035.banking.ui.feature_profile

import by.alexandr7035.banking.domain.features.profile.model.CompactProfile
import by.alexandr7035.banking.domain.features.profile.model.ProfileTier
import by.alexandr7035.banking.ui.core.extensions.splitStringWithDivider

data class ProfileUi(
    val name: String,
    val id: String,
    val email: String,
    val profilePicUrl: String,
    val tier: String = "Basic",
    val balance: Float = 2500f
) {
    companion object {
        fun mock() = ProfileUi(
            name = "Alexander Michael",
            id = "0896 2102 7821",
            email = "test@example.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey",
            tier = "Basic"
        )

        fun mapFromDomain(profile: CompactProfile) = ProfileUi(
            name = "${profile.firstName} ${profile.lastName}",
            id = profile.id.splitStringWithDivider(),
            email = profile.email,
            profilePicUrl = profile.profilePicUrl,
            tier = when (profile.tier) {
                ProfileTier.BASIC -> "Basic"
                ProfileTier.PREMIUM -> "Premium"
            }
        )
    }
}

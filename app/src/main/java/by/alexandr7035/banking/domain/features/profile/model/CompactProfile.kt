package by.alexandr7035.banking.domain.features.profile.model

data class CompactProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val profilePicUrl: String,
    val tier: ProfileTier,
//    val balanceFlow: Flow<Float>
)
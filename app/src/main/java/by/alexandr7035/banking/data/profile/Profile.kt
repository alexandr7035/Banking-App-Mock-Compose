package by.alexandr7035.banking.data.profile

data class Profile(
    val name: String,
    val id: String,
    val email: String,
    val profilePicUrl: String,
    val tier: String = "Basic",
    val balance: Float = 2500f
) {
    companion object {
        fun mock() = Profile(
            name = "Alexander Michael",
            id = "0896 2102 7821",
            email = "test@example.com",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey",
            tier = "Basic"
        )
    }
}

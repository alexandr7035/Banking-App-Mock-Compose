package by.alexandr7035.banking.domain.repository.savings

data class Saving(
    val title: String,
    val description: String,
    val completedPercentage: Float,
    val iconUrl: String
) {
    val isCompleted: Boolean
        get() = completedPercentage >= 1f
}

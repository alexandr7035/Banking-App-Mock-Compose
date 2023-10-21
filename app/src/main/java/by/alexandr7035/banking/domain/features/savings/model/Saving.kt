package by.alexandr7035.banking.domain.features.savings.model

data class Saving(
    val id: Long,
    val title: String,
    val description: String,
    val completedPercentage: Float,
    val iconUrl: String,
    val linkedCardId: String? = null
) {
    val isCompleted: Boolean
        get() = completedPercentage >= 1f
}

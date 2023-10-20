package by.alexandr7035.banking.ui.feature_savings.model

import by.alexandr7035.banking.domain.repository.savings.Saving

data class SavingUi(
    val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val donePercentage: Float,
    val isCompleted: Boolean
) {
    companion object {
        fun mock(donePercentage: Float = 0.75F): SavingUi {
            return SavingUi(
                id = 0,
                title = "Buy Playstation",
                description = "Slim 1 TB 56 Games",
                imageUrl = "https://svgur.com/i/y7P.svg",
                donePercentage = donePercentage,
                isCompleted = false
            )
        }

        fun mapFromDomain(saving: Saving): SavingUi {
            return SavingUi(
                id = saving.id,
                title = saving.title,
                description = saving.description,
                imageUrl = saving.iconUrl,
                donePercentage = saving.completedPercentage,
                isCompleted = saving.isCompleted
            )
        }
    }
}

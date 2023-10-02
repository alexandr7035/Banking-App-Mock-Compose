package by.alexandr7035.banking.ui.feature_savings.model

data class SavingUi(
    val title: String,
    val description: String,
    val imageUrl: String,
    val donePercentage: Float
) {
    companion object {
        fun mock(): SavingUi {
            return SavingUi(
                title = "Buy Playstation",
                description = "Slim 1 TB 56 Games",
                imageUrl = "https://svgur.com/i/y7P.svg",
                donePercentage = 0.75F
            )
        }
    }
}

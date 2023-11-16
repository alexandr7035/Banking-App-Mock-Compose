package by.alexandr7035.banking.domain.features.account.send_money

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount

// Draft impl of proposed send values depending on card balance
class GetSuggestedSendValuesForCardBalance {
    fun execute(balance: MoneyAmount): Set<MoneyAmount> {
        return when (balance.value) {
            0f -> emptySet()

            // Suggest only min and max value
            in (0f..1f) -> {
                val middleValue = balance.value / 2

                setOf(
                    0.01F,
                    middleValue,
                    balance.value,
                ).map {
                    MoneyAmount(it)
                }.toSet()
            }

            in 1f..100f -> {
                val stepValue = 5F

                val suggestedValues = generateSequence(5f) { it + stepValue }
                    .takeWhile { it <= balance.value }
                    .map { MoneyAmount(it) }
                    .take(7)
                    .toSet()


                setOf(MoneyAmount(1f)) + suggestedValues + setOf(balance)
            }
            in 100F..10000F -> {
                val maxValue = balance.value
                val stepValue = 50F

                val suggestedValues = generateSequence(50f) { it + stepValue }
                    .takeWhile { it <= maxValue }
                    .map { MoneyAmount(it) }
                    .take(7)
                    .toSet()


                setOf(MoneyAmount(1f)) + suggestedValues + setOf(balance)
            }

            else -> {
                val maxValue = balance.value
                val stepValue = 100F

                val suggestedValues = generateSequence(50f) { it + stepValue }
                    .takeWhile { it <= maxValue }
                    .map { MoneyAmount(it) }
                    .take(8)
                    .toSet()


                setOf(MoneyAmount(1f)) + suggestedValues
            }
        }
    }
}
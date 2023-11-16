package by.alexandr7035.banking.ui.feature_account

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount

data class AmountPickersState(
    val selectedAmount: MoneyAmount = MoneyAmount(0F),
    val proposedValues: Set<MoneyAmount> = emptySet(),
)

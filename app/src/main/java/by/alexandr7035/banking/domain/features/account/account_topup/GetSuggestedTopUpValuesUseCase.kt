package by.alexandr7035.banking.domain.features.account.account_topup

import by.alexandr7035.banking.domain.features.account.model.BalanceValue

class GetSuggestedTopUpValuesUseCase {
    // Use unordered collection with unique values
    fun execute(): Set<BalanceValue.LongBalance> {
        // Here may be additional logic, like include recent sent values
        return (50..450 step 50).map {
            BalanceValue.LongBalance(it.toLong())
        }.toSet()
    }
}
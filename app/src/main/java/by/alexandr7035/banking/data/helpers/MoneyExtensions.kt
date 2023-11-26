package by.alexandr7035.banking.data.helpers

import by.alexandr7035.banking.domain.features.account.model.MoneyAmount

inline fun <T> Iterable<T>.sumMoneyAmounts(selector: (T) -> MoneyAmount): MoneyAmount {
    var sum = MoneyAmount(0f)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
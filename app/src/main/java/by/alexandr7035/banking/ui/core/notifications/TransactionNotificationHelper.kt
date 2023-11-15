package by.alexandr7035.banking.ui.core.notifications

import android.content.Context
import by.alexandr7035.banking.domain.core.ErrorType
import by.alexandr7035.banking.domain.features.account.model.MoneyAmount
import by.alexandr7035.banking.domain.features.transactions.model.TransactionType
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.core.extensions.maskCardId
import by.alexandr7035.banking.ui.feature_account.MoneyAmountUi

object TransactionNotificationHelper {
    fun successMessage(
        context: Context,
        transactionType: TransactionType,
        amount: MoneyAmount,
        cardId: String,
    ): NotificationUi {
        return when (transactionType) {
            TransactionType.SEND -> TODO()
            TransactionType.RECEIVE -> TODO()
            TransactionType.TOP_UP -> {
                NotificationUi(
                    title = "Top up successful!",
                    message = "You've received ${MoneyAmountUi.mapFromDomain(amount).amountStr} on ${cardId.maskCardId()}",
                )
            }
        }
    }

    fun errorMessage(
        context: Context,
        error: Throwable
    ): NotificationUi {
        val errorUi = ErrorType.fromThrowable(error).asUiTextError().asString(context)
        return NotificationUi(
            title = "Transaction failed",
            message = errorUi
        )
    }

    // Fore cases when tx launched in foreground service
    fun pending(): NotificationUi {
        return NotificationUi(
            title = "Operation in progress",
            message = "Please, wait a minute..."
        )
    }
}
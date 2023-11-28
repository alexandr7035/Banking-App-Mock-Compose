package by.alexandr7035.banking.ui.feature_cards.helpers

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText

object CardUiHelpers {
    // https://dev.to/benyam7/formatting-credit-card-number-input-in-jetpack-compose-android-2nal
    fun formatCardNumber(text: AnnotatedString): TransformedText {

        val trimmed = if (text.text.length >= 16) text.text.substring(0..15) else text.text
        var out = ""

        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i % 4 == 3 && i != 15) out += "-"
        }
        val creditCardOffsetTranslator = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 3) return offset
                if (offset <= 7) return offset + 1
                if (offset <= 11) return offset + 2
                if (offset <= 16) return offset + 3
                return 19
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 4) return offset
                if (offset <= 9) return offset - 1
                if (offset <= 14) return offset - 2
                if (offset <= 19) return offset - 3
                return 16
            }
        }

        return TransformedText(AnnotatedString(out), creditCardOffsetTranslator)
    }

    fun detectCardNetwork(cardNumber:String): CardNetwork {
        return when {
//            Regex("^3[47][0-9]{13}\$").matches(cardNumber) -> CardNetwork.AMEX
            Regex("^4[0-9]{12}(?:[0-9]{3})?$").matches(cardNumber) -> CardNetwork.VISA
            Regex("^(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}$").matches(cardNumber) -> CardNetwork.MAESTRO
            Regex("^(5[1-5][0-9]{14}|2(22[1-9][0-9]{12}|2[3-9][0-9]{13}|[3-6][0-9]{14}|7[0-1][0-9]{13}|720[0-9]{12}))$").matches(cardNumber) -> CardNetwork.MASTERCARD
            // Add more card types as needed
            else -> CardNetwork.UNKNOWN
        }
    }
}

enum class CardNetwork {
    VISA,
    MAESTRO,
    MASTERCARD,
    UNKNOWN
}
package by.alexandr7035.banking.ui.extensions

fun String.formatCardNumber(): String {
    val formattedStringBuilder = StringBuilder()
    var count = 0

    for (char in this) {
        if (count > 0 && count % 4 == 0) {
            formattedStringBuilder.append(' ')
        }
        formattedStringBuilder.append(char)
        count++
    }

    return formattedStringBuilder.toString()
}
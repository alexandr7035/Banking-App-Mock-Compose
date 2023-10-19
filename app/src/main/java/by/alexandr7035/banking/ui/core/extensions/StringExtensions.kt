package by.alexandr7035.banking.ui.core.extensions

fun String.splitStringWithDivider(
    groupCharCount: Int = 4,
    divider: Char = ' '
): String {
    val formattedStringBuilder = StringBuilder()
    var count = 0

    for (char in this) {
        if (count > 0 && count % groupCharCount == 0) {
            formattedStringBuilder.append(divider)
        }
        formattedStringBuilder.append(char)
        count++
    }

    return formattedStringBuilder.toString()
}
package by.alexandr7035.banking.ui.validation

import android.text.TextUtils
import android.util.Patterns

object FormValidators {
    fun isEmailValid(email: String): Boolean {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 8) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.firstOrNull { it.isLetter() } == null) return false

        return true
    }
}
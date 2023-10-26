package by.alexandr7035.banking.data.app_lock

import android.content.SharedPreferences
import android.util.Base64
import by.alexandr7035.banking.domain.features.app_lock.AppLockRepository
import by.alexandr7035.banking.domain.features.app_lock.AuthenticationResult

class AppLockRepositoryImpl(
    private val securedPreferences: SharedPreferences
): AppLockRepository {

    // TODO set pin method
    init {
        savePin("1122")
    }

    override fun authenticateWithPin(pin: String): AuthenticationResult {
        return if (isPinValid(pin)) {
            AuthenticationResult.Success
        }
        else {
            // TODO attempts
            AuthenticationResult.Failure(remainingAttempts = null)
        }
    }

    private fun savePin(pin: String) {
        val salt = CryptoUtils.generateSalt()
        val saltedPin = pin.toByteArray() + salt

        val hashedPin = CryptoUtils.sha256(saltedPin)
        val encodedPinHash = Base64.encodeToString(hashedPin, Base64.DEFAULT)
        val encodedSalt = Base64.encodeToString(salt, Base64.DEFAULT)

        securedPreferences.edit()
            .putString(PIN_KEY, encodedPinHash)
            .putString(PIN_SALT_KEY, encodedSalt)
            .apply()
    }

    private fun isPinValid(pin: String): Boolean {
        val encodedSalt = securedPreferences.getString(PIN_SALT_KEY, null)
        val encodedHashedPin = securedPreferences.getString(PIN_KEY, null)

        val salt = Base64.decode(encodedSalt, Base64.DEFAULT)
        val storedHashedPin = Base64.decode(encodedHashedPin, Base64.DEFAULT)

        val enteredHashedPin = CryptoUtils.sha256(pin.toByteArray() + salt)

        return storedHashedPin contentEquals enteredHashedPin
    }

    companion object {
        private const val PIN_KEY = "PIN_KEY"
        private const val PIN_SALT_KEY= "PIN_SALT"
    }
}
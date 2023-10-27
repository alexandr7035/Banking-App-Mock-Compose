package by.alexandr7035.banking.data.app_lock

import android.content.SharedPreferences
import android.util.Base64
import by.alexandr7035.banking.domain.features.app_lock.AppLockRepository
import by.alexandr7035.banking.domain.features.app_lock.AuthenticationResult

class AppLockRepositoryImpl(
    private val securedPreferences: SharedPreferences
) : AppLockRepository {

    // TODO set pin method
    init {
        savePin("1122")
    }

    override fun authenticateWithPin(pin: String): AuthenticationResult {
        return if (isPinValid(pin)) {
            AuthenticationResult.Success
        } else {
            // TODO attempts
            AuthenticationResult.Failure(remainingAttempts = null)
        }
    }

    private fun savePin(pin: String) {
        val salt = CryptoUtils.generateSalt()

        val secretKey = CryptoUtils.generatePbkdf2Key(
            passphraseOrPin = pin.toCharArray(),
            salt = salt
        )

        val encodedPinData = Base64.encodeToString(secretKey.encoded, Base64.DEFAULT)
        val encodedSalt = Base64.encodeToString(salt, Base64.DEFAULT)

        securedPreferences.edit()
            .putString(PIN_KEY, encodedPinData)
            .putString(PIN_SALT_KEY, encodedSalt)
            .apply()
    }

    private fun isPinValid(pin: String): Boolean {
        val storedSalt = securedPreferences.getString(PIN_SALT_KEY, null)
        val decodedSalt = Base64.decode(storedSalt, Base64.DEFAULT)

        val storedPinData = securedPreferences.getString(PIN_KEY, null)
        val decodedPinData = Base64.decode(storedPinData, Base64.DEFAULT)

        val enteredPinData = CryptoUtils.generatePbkdf2Key(pin.toCharArray(), decodedSalt)

        return decodedPinData contentEquals enteredPinData.encoded
    }

    override fun checkIfAppLocked(): Boolean {
        return securedPreferences.getString(PIN_SALT_KEY, null) != null
                && securedPreferences.getString(PIN_KEY, null) != null
    }

    companion object {
        private const val PIN_KEY = "PIN_KEY"
        private const val PIN_SALT_KEY = "PIN_SALT"
    }
}
package by.alexandr7035.banking.data.app_lock

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

object CryptoUtils {
    fun sha256(byteArray: ByteArray): ByteArray {
        val digest = try {
            MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            MessageDigest.getInstance("SHA")
        }

        return with(digest) {
            update(byteArray)
            digest()
        }
    }

    fun generateSalt(lengthByte: Int = 32): ByteArray {
        val random = SecureRandom()
        val salt = ByteArray(lengthByte)

        random.nextBytes(salt)

        return salt
    }
}
package by.alexandr7035.banking.data.app_lock

import android.os.Build
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import javax.crypto.SecretKey
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

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

    // KDF-function
    fun generatePbkdf2Key(
        passphraseOrPin: CharArray,
        salt: ByteArray,
        iterations: Int = PBKDF2_DEFAULT_ITERATIONS,
        outputKeyLength: Int = PBKDF2_DEFAULT_KEY_LENGTH
    ): SecretKey {
        val secretKeyFactory =
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                SecretKeyFactory.getInstance("PBKDF2withHmacSHA1")
            } else {
                SecretKeyFactory.getInstance("PBKDF2withHmacSHA256")
            }

        val keySpec = PBEKeySpec(passphraseOrPin, salt, iterations, outputKeyLength)
        return secretKeyFactory.generateSecret(keySpec)
    }

    private const val PBKDF2_DEFAULT_ITERATIONS = 10000
    private const val PBKDF2_DEFAULT_KEY_LENGTH = 256
}
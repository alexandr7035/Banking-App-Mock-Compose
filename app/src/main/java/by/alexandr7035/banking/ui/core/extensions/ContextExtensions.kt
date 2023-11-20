package by.alexandr7035.banking.ui.core.extensions

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showToast(@StringRes stringRes: Int) {
    Toast.makeText(this, getString(stringRes), Toast.LENGTH_SHORT).show()
}

fun Context.findActivity(): FragmentActivity? = when (this) {
    is FragmentActivity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

fun Context.openBiometricsSettings() {
    val intent = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.R -> Intent(Settings.ACTION_BIOMETRIC_ENROLL)
        else -> Intent(Settings.ACTION_SECURITY_SETTINGS)
    }
    startActivity(intent)
}

fun Context.vibrate(vibrationMode: VibrationMode = VibrationMode.SHORT) {
    // Time mills for modes
    val vibrationTimeMills = when (vibrationMode) {
        VibrationMode.SHORT -> 200
        VibrationMode.MEDIUM -> 500
        VibrationMode.LONG -> 1000
    }.toLong()

    val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val vibratorManager = this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
        vibratorManager.defaultVibrator
    } else {
        this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
    }

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(VibrationEffect.createOneShot(vibrationTimeMills, VibrationEffect.DEFAULT_AMPLITUDE))
    } else {
        vibrator.vibrate(vibrationTimeMills)
    }
}

enum class VibrationMode {
    SHORT,
    MEDIUM,
    LONG
}
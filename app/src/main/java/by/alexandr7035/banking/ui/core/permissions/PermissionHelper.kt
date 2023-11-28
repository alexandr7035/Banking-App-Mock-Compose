package by.alexandr7035.banking.ui.core.permissions

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.alexandr7035.banking.data.app.AppSettignsRepository
import by.alexandr7035.banking.ui.core.extensions.findActivity
import com.permissionx.guolindev.PermissionX

class PermissionHelper(
    private val appSettings: AppSettignsRepository
) {
    fun checkIfPermissionGranted(
        context: Context,
        permission: String,
    ): CheckPermissionResult {
        val activity = context.findActivity() ?: return CheckPermissionResult.SHOULD_ASK_PERMISSION

        // Permission granted
        return if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
            CheckPermissionResult.PERMISSION_ALREADY_GRANTED
        }
        // Permission not granted
        else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                // TRUE the user has denied the permission previously but
                // has NOT checked the "Never Ask Again" checkbox.
                CheckPermissionResult.SHOULD_ASK_PERMISSION

                // shouldShowRequestPermissionRationale() is FALSE in 2 cases:
                // 1) user asks for first time 2) User denied the permission previously AND selected "never ask again"
                // Need to use persistent storage to distinguish the cases
            } else {
                // User denied the permission previously AND selected "never ask again"
                if (appSettings.isAppPermissionAlreadyAsked(permission)) {
                    CheckPermissionResult.SHOULD_REDIRECT_TO_SETTINGS
                }
                // When user is requesting permission for the first time
                else {
                    CheckPermissionResult.SHOULD_ASK_PERMISSION
                }
            }
        }

    }

    fun askForPermission(
        context: Context,
        permission: String,
        onResult: (AskPermissionResult) -> Unit
    ) {
        val activity = context.findActivity()

        if (activity == null) {
            onResult(AskPermissionResult.REJECTED)
        }

        activity?.let {
            PermissionX.init(activity)
                .permissions(permission)
                .request { allGranted, grantedList, deniedList ->
                    appSettings.setPermissionAsked(permission)

                    if (allGranted) {
                        onResult(AskPermissionResult.GRANTED)
                    } else {
                        onResult(AskPermissionResult.REJECTED)
                    }
                }
        }
    }
}

enum class CheckPermissionResult {
    SHOULD_ASK_PERMISSION,
    SHOULD_REDIRECT_TO_SETTINGS,
    PERMISSION_ALREADY_GRANTED,
}

enum class AskPermissionResult {
    GRANTED,
    REJECTED
}

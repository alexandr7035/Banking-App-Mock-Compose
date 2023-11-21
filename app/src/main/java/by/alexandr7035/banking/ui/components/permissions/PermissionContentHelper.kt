package by.alexandr7035.banking.ui.components.permissions

import androidx.annotation.DrawableRes
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.core.resources.UiText

object PermissionContentHelper {
    fun getPermissionExplanationContent(permission: String): PermissionExplanation{
        return when (permission) {
            android.Manifest.permission.CAMERA -> {
                PermissionExplanation(
                    // FIXME
                    icon = R.drawable.ic_camera,
                    permissionName = UiText.StringResource(R.string.camera),
                    explanation = UiText.StringResource(R.string.camera_permission_explanation)
                )
            }

            else -> error("Explanation content not implemented for permission $permission")
        }
    }
}

data class PermissionExplanation(
    @DrawableRes val icon: Int,
    val permissionName: UiText,
    val explanation: UiText,
)
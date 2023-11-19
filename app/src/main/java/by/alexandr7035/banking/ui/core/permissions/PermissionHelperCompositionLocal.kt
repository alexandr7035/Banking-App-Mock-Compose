package by.alexandr7035.banking.ui.core.permissions

import androidx.compose.runtime.compositionLocalOf

val LocalPermissionHelper = compositionLocalOf<PermissionHelper> { error("No PermissionHelper provided") }
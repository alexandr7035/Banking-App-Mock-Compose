package by.alexandr7035.banking.ui.components.permissions

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.core.effects.OnLifecycleEventEffect
import by.alexandr7035.banking.ui.core.extensions.openAppSystemSettings
import by.alexandr7035.banking.ui.core.permissions.CheckPermissionResult
import by.alexandr7035.banking.ui.core.permissions.LocalPermissionHelper
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionExplanationDialog(
    permission: String,
    onDismiss: (isProvided: Boolean) -> Unit
) {
    val permissionHelper = LocalPermissionHelper.current
    val context = LocalContext.current

    val dialogState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    val showGoToSettingsLabel = remember {
        mutableStateOf(false)
    }

    ModalBottomSheet(
        onDismissRequest = {
            val permissionResult = permissionHelper.checkIfPermissionGranted(context, permission)
            onDismiss(permissionResult == CheckPermissionResult.PERMISSION_ALREADY_GRANTED)
        },
        sheetState = dialogState,
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        PermissionExplanationDialog_Ui(
            permission = permission,
            onCheckPermission = {
                permissionHelper.checkIfPermissionGranted(context, permission)
            },
            onDismiss = {
                onDismiss(it)
            },
            askForPermission = {
                permissionHelper.askForPermission(context, permission, {})
            },
            requireSettingsLabel = showGoToSettingsLabel.value
        )
    }

    // Used when user navigates back from settings
    OnLifecycleEventEffect() { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            when (permissionHelper.checkIfPermissionGranted(context, permission)) {
                CheckPermissionResult.SHOULD_ASK_PERMISSION -> {
                    showGoToSettingsLabel.value = false
                }
                CheckPermissionResult.SHOULD_REDIRECT_TO_SETTINGS -> {
                    showGoToSettingsLabel.value = true
                }
                CheckPermissionResult.PERMISSION_ALREADY_GRANTED -> {
                    onDismiss(true)
                }
            }
        }
    }
}

@Composable
fun PermissionExplanationDialog_Ui(
    permission: String,
    onCheckPermission: () -> CheckPermissionResult = { CheckPermissionResult.SHOULD_ASK_PERMISSION },
    askForPermission: () -> Unit = {},
    requireSettingsLabel: Boolean = false,
    onDismiss: (isGranted: Boolean) -> Unit = {},
) {
    val context = LocalContext.current

    val explanationContent = PermissionContentHelper.getPermissionExplanationContent(permission)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(
                horizontal = 24.dp, vertical = 16.dp
            ),
    ) {
        Text(
            text = stringResource(R.string.provide_permission_template, explanationContent.permissionName.asString()), style = TextStyle(
                fontSize = 18.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF262626),
                textAlign = TextAlign.Center,
            ), modifier = Modifier.padding(vertical = 16.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = explanationContent.icon),
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5))
                    .padding(16.dp),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )

            Text(
                text = explanationContent.explanation.asString(), style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                )
            )
        }

        if (requireSettingsLabel) {
            Text(
                text = stringResource(R.string.permissions_settings_explanation),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    color = Color(0xFFFF9800),
                    fontSize = 14.sp
                ),
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }
        else {
            Spacer(modifier = Modifier.height(16.dp))
        }

        PrimaryButton(
            onClick = {
                when (onCheckPermission()) {
                    CheckPermissionResult.SHOULD_ASK_PERMISSION -> {
                        askForPermission()
                    }

                    CheckPermissionResult.SHOULD_REDIRECT_TO_SETTINGS -> {
                        context.openAppSystemSettings()
                    }

                    CheckPermissionResult.PERMISSION_ALREADY_GRANTED -> {
                        onDismiss(true)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.grant_permission)
        )
    }
}

@Preview
@Composable
fun PermissionExplanationDialog_Preview() {
    ScreenPreview {
        PermissionExplanationDialog_Ui(
            permission = android.Manifest.permission.CAMERA,
            requireSettingsLabel = false
        )
    }
}

@Preview
@Composable
fun PermissionExplanationDialog_GoToSettings_Preview() {
    ScreenPreview {
        PermissionExplanationDialog_Ui(
            permission = android.Manifest.permission.CAMERA,
            requireSettingsLabel = true,
        )
    }
}
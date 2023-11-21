package by.alexandr7035.banking.ui.feature_app_settings

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import by.alexandr7035.banking.BuildConfig
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryButton
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.permissions.PermissionContentHelper
import by.alexandr7035.banking.ui.core.effects.OnLifecycleEventEffect
import by.alexandr7035.banking.ui.core.extensions.openAppSystemSettings
import by.alexandr7035.banking.ui.core.extensions.showToast
import by.alexandr7035.banking.ui.core.permissions.AskPermissionResult
import by.alexandr7035.banking.ui.core.permissions.CheckPermissionResult
import by.alexandr7035.banking.ui.core.permissions.LocalPermissionHelper
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun AppSettingsScreen(onBack: () -> Unit) {
    val permissionHelper = LocalPermissionHelper.current
    val context = LocalContext.current

    val permissionsState = remember {
        mutableStateMapOf<String, Boolean?>().apply {
            put(android.Manifest.permission.CAMERA, null)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                put(android.Manifest.permission.POST_NOTIFICATIONS, null)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SecondaryToolBar(
                onBack = onBack,
                title = UiText.StringResource(R.string.app_settings)
            )
        }
    ) { pv ->
        AppSettingsScreen_Ui(
            modifier = Modifier.padding(pv),
            permissionsState = permissionsState,
            onRequestPermission = {
                when (permissionHelper.checkIfPermissionGranted(context, it)) {
                    CheckPermissionResult.SHOULD_ASK_PERMISSION -> {
                        permissionHelper.askForPermission(context, it) { result ->
                            when (result) {
                                AskPermissionResult.GRANTED -> {
                                    context.showToast(R.string.permission_granted)
                                }

                                AskPermissionResult.REJECTED -> {
                                    context.showToast(R.string.permission_rejected)
                                }
                            }
                        }
                    }
                    CheckPermissionResult.SHOULD_REDIRECT_TO_SETTINGS -> {
                        context.openAppSystemSettings()
                    }

                    CheckPermissionResult.PERMISSION_ALREADY_GRANTED -> {}
                }
            }
        )
    }

    OnLifecycleEventEffect() { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            permissionsState.keys.forEach { permission ->
                permissionsState[permission] = null

                val res = permissionHelper.checkIfPermissionGranted(
                    context, permission
                )

                permissionsState[permission] = res == CheckPermissionResult.PERMISSION_ALREADY_GRANTED
            }
        }
    }
}

@Composable
fun AppSettingsScreen_Ui(
    modifier: Modifier,
    permissionsState: Map<String, Boolean?> = emptyMap(),
    onRequestPermission: (permission: String) -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .then(
                Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = 24.dp,
                        vertical = 16.dp
                    )
                    .verticalScroll(rememberScrollState())
            )
    ) {

        SettingSection(stringResource(R.string.permissions))

        permissionsState.forEach {
            PermissionItem(
                permission = it.key,
                isGranted = it.value,
                onRequestPermission = {
                    onRequestPermission(it.key)
                }
            )
        }

        Spacer(Modifier.weight(1f))
        Spacer(Modifier.height(56.dp))

        SecondaryButton(
            onClick = {
                context.openAppSystemSettings()
            },
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.app_system_settings)
        )

        Spacer(Modifier.height(16.dp))

        Text(
            text = "${stringResource(id = R.string.app_name)} v${BuildConfig.VERSION_NAME}",
            style = TextStyle(
                textAlign = TextAlign.Center,
                fontFamily = primaryFontFamily,
                fontSize = 14.sp
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun PermissionItem(
    permission: String,
    isGranted: Boolean?,
    onRequestPermission: () -> Unit,
) {
    val permissionUi = PermissionContentHelper.getPermissionExplanationContent(permission)
    val title = permissionUi.permissionName.asString()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title
        )

        Spacer(Modifier.weight(1f))

        if (isGranted != null) {
            if (!isGranted) {
                Switch(
                    checked = isGranted,
                    onCheckedChange = {
                        onRequestPermission()
                    }
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_success_mark),
                    contentDescription = stringResource(id = R.string.permission_granted),
                    tint = Color(0xFF00B16E),
                    modifier = Modifier.size(36.dp)
                )
            }
        } else {
            DotsProgressIndicator(
                circleSize = 12.dp,
                travelDistance = 4.dp,
                spaceBetween = 2.dp
            )
        }
    }
}

@Composable
fun SettingSection(title: String) {
    HorizontalDivider(Modifier.padding(top = 24.dp))

    Text(
        text = title,
        style = TextStyle(
            fontSize = 16.sp,
            fontFamily = primaryFontFamily,
            fontWeight = FontWeight(500),
            color = Color(0xFF333333),
        ),
        modifier = Modifier.padding(
            vertical = 16.dp
        )
    )
}

@Composable
@Preview
fun AppSettingsScreen_Preview() {
    ScreenPreview {
        AppSettingsScreen_Ui(
            modifier = Modifier.fillMaxSize(),
            permissionsState = mapOf(
                android.Manifest.permission.CAMERA to true,
                android.Manifest.permission.POST_NOTIFICATIONS to null,
            ),
        )
    }
}
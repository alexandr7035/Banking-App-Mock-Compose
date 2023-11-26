package by.alexandr7035.banking.ui.feature_app_settings

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.BuildConfig
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.features.app_lock.model.BiometricsAvailability
import by.alexandr7035.banking.ui.app_host.host_utils.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.components.DotsProgressIndicator
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SecondaryButton
import by.alexandr7035.banking.ui.components.SecondaryToolBar
import by.alexandr7035.banking.ui.components.TextBtn
import by.alexandr7035.banking.ui.components.permissions.PermissionContentHelper
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.core.effects.OnLifecycleEventEffect
import by.alexandr7035.banking.ui.core.extensions.findActivity
import by.alexandr7035.banking.ui.core.extensions.openAppSystemSettings
import by.alexandr7035.banking.ui.core.extensions.openBiometricsSettings
import by.alexandr7035.banking.ui.core.extensions.showToast
import by.alexandr7035.banking.ui.core.permissions.AskPermissionResult
import by.alexandr7035.banking.ui.core.permissions.CheckPermissionResult
import by.alexandr7035.banking.ui.core.permissions.LocalPermissionHelper
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsHelper
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsIntent
import by.alexandr7035.banking.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun AppSettingsScreen(
    viewModel: AppSettingsViewModel = koinViewModel(),
    onBack: () -> Unit
) {
    val permissionHelper = LocalPermissionHelper.current
    val context = LocalContext.current
    val snackBarState = LocalScopedSnackbarState.current

    val state = viewModel.state.collectAsStateWithLifecycle().value

    val permissionsState = remember {
        mutableStateMapOf<String, Boolean?>().apply {
            put(android.Manifest.permission.CAMERA, null)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                put(android.Manifest.permission.POST_NOTIFICATIONS, null)
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        SecondaryToolBar(
            onBack = onBack, title = UiText.StringResource(R.string.app_settings)
        )
    }) { pv ->
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
            },
            onBiometricsIntent = {
                viewModel.emitBiometricsIntent(it)
            },
            state = state
        )
    }

    OnLifecycleEventEffect { _, event ->
        if (event == Lifecycle.Event.ON_RESUME) {
            permissionsState.keys.forEach { permission ->
                permissionsState[permission] = null

                val res = permissionHelper.checkIfPermissionGranted(
                    context, permission
                )

                permissionsState[permission] = res == CheckPermissionResult.PERMISSION_ALREADY_GRANTED
            }

            viewModel.emitBiometricsIntent(BiometricsIntent.RefreshBiometricsAvailability)
        }
    }

    EventEffect(
        event = state.biometricsAuthEvent,
        onConsumed = viewModel::consumeBiometricAuthEvent,
    ) { biometricsRes ->
        if (biometricsRes is BiometricAuthResult.Failure) {
            snackBarState.show(biometricsRes.error, SnackBarMode.Negative)
        }

        viewModel.emitBiometricsIntent(BiometricsIntent.RefreshBiometricsAvailability)
    }
}

@Composable
fun AppSettingsScreen_Ui(
    modifier: Modifier,
    permissionsState: Map<String, Boolean?> = emptyMap(),
    onRequestPermission: (permission: String) -> Unit = {},
    onBiometricsIntent: (BiometricsIntent) -> Unit = {},
    state: AppSettingsState,
) {
    val context = LocalContext.current

    Column(
        modifier = modifier.then(
            Modifier
                .fillMaxSize()
                .padding(
                    horizontal = 24.dp, vertical = 16.dp
                )
                .verticalScroll(rememberScrollState())
        )
    ) {

        SettingSection(stringResource(R.string.permissions))

        permissionsState.forEach {
            PermissionItem(permission = it.key, isGranted = it.value, onRequestPermission = {
                onRequestPermission(it.key)
            })
        }

        SettingSection(stringResource(R.string.app_lock))
        BiometricsItem(
            biometricsAvailability = state.biometricsAvailability,
            isAppLocked = state.isAppLockedWithBiometrics,
            onIntent = onBiometricsIntent,
            promptUi = state.biometricPrompt
        )

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
                textAlign = TextAlign.Center, fontFamily = primaryFontFamily, fontSize = 14.sp
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun PermissionItem(
    permission: String,
    isGranted: Boolean?,
    onRequestPermission: () -> Unit,
) {
    val permissionUi = PermissionContentHelper.getPermissionExplanationContent(permission)
    val title = permissionUi.permissionName.asString()

    Row(
        modifier = Modifier
            .fillMaxWidth()
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
                    })
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
                circleSize = 12.dp, travelDistance = 4.dp, spaceBetween = 2.dp
            )
        }
    }
}

@Composable
private fun BiometricsItem(
    biometricsAvailability: BiometricsAvailability,
    promptUi: BiometricsPromptUi,
    isAppLocked: Boolean,
    onIntent: (BiometricsIntent) -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.biometric_lock)
            )

            Spacer(Modifier.weight(1f))

            when (biometricsAvailability) {
                BiometricsAvailability.Available -> {
                    Switch(
                        checked = isAppLocked,
                        onCheckedChange = {
                            val activity = context.findActivity()
                            activity?.let {
                                BiometricsHelper.showPrompt(
                                    activity = it,
                                    prompt = promptUi,
                                    onError = { error ->
                                        onIntent(
                                            BiometricsIntent.ConsumeAuthResult(
                                                BiometricAuthResult.Failure(error)
                                            )
                                        )
                                    },
                                    onSuccess = {
                                        onIntent(
                                            BiometricsIntent.ConsumeAuthResult(
                                                BiometricAuthResult.Success
                                            )
                                        )
                                    })
                            }
                        }
                    )
                }

                BiometricsAvailability.Checking -> {
                    DotsProgressIndicator(
                        circleSize = 12.dp, travelDistance = 4.dp, spaceBetween = 2.dp
                    )
                }

                BiometricsAvailability.NotAvailable -> {
                    Text(
                        text = stringResource(R.string.unavailable),
                        color = Color.Red
                    )
                }

                BiometricsAvailability.NotEnabled -> {
                    TextBtn(
                        onClick = {
                            context.openBiometricsSettings()
                        },
                        modifier = Modifier.wrapContentSize(),
                        text = stringResource(R.string.enable)
                    )
                }
            }
        }
    }
    if (biometricsAvailability == BiometricsAvailability.NotEnabled) {
        Text(
            text = stringResource(R.string.biometrics_enable_explanation),
            style = TextStyle(
                textAlign = TextAlign.Start,
                color = Color(0xFFFF9800),
                fontSize = 16.sp
            ),
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun SettingSection(title: String) {
    HorizontalDivider(Modifier.padding(top = 24.dp))

    Text(
        text = title, style = TextStyle(
            fontSize = 16.sp,
            fontFamily = primaryFontFamily,
            fontWeight = FontWeight(500),
            color = Color(0xFF333333),
        ), modifier = Modifier.padding(
            vertical = 16.dp
        )
    )
}

@SuppressLint("InlinedApi")
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
            state = AppSettingsState(
                biometricsAvailability = BiometricsAvailability.Checking,
                isAppLockedWithBiometrics = false
            )
        )
    }
}

@Composable
@Preview
fun AppSettingsScreen_BiometricButton_Preview() {
    ScreenPreview {
        val prompt = BiometricsPromptUi(
            cancelBtnText = UiText.StringResource(R.string.cancel),
            title = UiText.StringResource(R.string.unlock_app_biometrics)
        )

        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxSize(),
        ) {
            BiometricsItem(
                biometricsAvailability = BiometricsAvailability.NotEnabled,
                isAppLocked = false,
                promptUi = prompt
            )

            BiometricsItem(
                biometricsAvailability = BiometricsAvailability.Available,
                isAppLocked = true,
                promptUi = prompt
            )

            BiometricsItem(
                biometricsAvailability = BiometricsAvailability.Available,
                isAppLocked = false,
                promptUi = prompt
            )

            BiometricsItem(
                biometricsAvailability = BiometricsAvailability.NotAvailable,
                isAppLocked = false,
                promptUi = prompt
            )

            BiometricsItem(
                biometricsAvailability = BiometricsAvailability.Checking,
                isAppLocked = false,
                promptUi = prompt
            )
        }
    }
}
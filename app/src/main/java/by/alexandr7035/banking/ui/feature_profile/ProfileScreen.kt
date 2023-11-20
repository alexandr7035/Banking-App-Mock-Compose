package by.alexandr7035.banking.ui.feature_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.alexandr7035.banking.R
import by.alexandr7035.banking.domain.core.OperationResult
import by.alexandr7035.banking.domain.features.qr_codes.model.QrPurpose
import by.alexandr7035.banking.ui.app_host.host_utils.LocalScopedSnackbarState
import by.alexandr7035.banking.ui.components.FullscreenProgressBar
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.SettingButton
import by.alexandr7035.banking.ui.components.header.ScreenHeader
import by.alexandr7035.banking.ui.components.snackbar.SnackBarMode
import by.alexandr7035.banking.ui.core.error.asUiTextError
import by.alexandr7035.banking.ui.core.resources.UiText
import by.alexandr7035.banking.ui.feature_logout.LogoutDialog
import by.alexandr7035.banking.ui.feature_logout.LogoutIntent
import by.alexandr7035.banking.ui.feature_profile.components.ProfileCard
import by.alexandr7035.banking.ui.feature_profile.model.ProfileUi
import by.alexandr7035.banking.ui.feature_profile.my_qr.ShowQrDialog
import by.alexandr7035.banking.ui.feature_profile.settings_list.SettingEntry
import by.alexandr7035.banking.ui.feature_profile.settings_list.SettingList
import by.alexandr7035.banking.ui.feature_profile.settings_list.SettingListItem
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import de.palm.composestateevents.EventEffect
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(),
    onSettingEntry: (entry: SettingEntry) -> Unit = {},
    onLogoutCompleted: () -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current
    val snackBarState = LocalScopedSnackbarState.current

    LaunchedEffect(Unit) {
        viewModel.emitIntent(ProfileScreenIntent.EnterScreen)
    }

    BoxWithConstraints {
        ProfileScreen_Ui(
            modifier = Modifier
                .width(maxWidth)
                .height(maxHeight),
            onSettingEntryClick = {
                onSettingEntry.invoke(it)
            },
            onLogoutIntent = {
                viewModel.emitLogoutIntent(it)
            },
            onShowQrDialog = {
                viewModel.emitIntent(ProfileScreenIntent.ToggleMyQrDialog(isShown = true))
            },
            state = state
        )

        if (state.logoutState.showLogoutDialog) {
            LogoutDialog(
                onDismiss = {
                    viewModel.emitLogoutIntent(LogoutIntent.ToggleLogoutDialog(isShown = false))
                },
                onConfirmLogout = {
                    viewModel.emitLogoutIntent(LogoutIntent.ConfirmLogOut)
                }
            )
        }

        if (state.logoutState.isLoading) {
            FullscreenProgressBar()
        }

        if (state.showMyQrDialog) {
            ShowQrDialog(
                onDismiss = {
                    viewModel.emitIntent(
                        ProfileScreenIntent.ToggleMyQrDialog(
                            isShown = false
                        )
                    )
                },
                qrPurpose = QrPurpose.PROFILE_CONNECTION
            )
        }

        EventEffect(
            event = state.logoutState.logoutEvent,
            onConsumed = viewModel::consumeLogoutEvent,
        ) { result ->

            when (result) {
                is OperationResult.Success -> {
                    onLogoutCompleted.invoke()
                }

                is OperationResult.Failure -> {
                    snackBarState.show(
                        message = result.error.errorType.asUiTextError().asString(context),
                        snackBarMode = SnackBarMode.Negative
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileScreen_Ui(
    modifier: Modifier,
    state: ProfileScreenState,
    onLogoutIntent: (intent: LogoutIntent) -> Unit = {},
    onSettingEntryClick: (entry: SettingEntry) -> Unit = {},
    onShowQrDialog: () -> Unit = {}
) {
    Column(
        modifier = modifier.then(
            Modifier
                .verticalScroll(rememberScrollState())
        )
    ) {
        ScreenHeader(toolbar = { ProfileToolBar() }) {
            ProfileCard(profile = state.profile, isLoading = state.isProfileLoading)
        }

        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth()
                .height(intrinsicSize = IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                icon = painterResource(id = R.drawable.ic_scan_qr),
                text = stringResource(R.string.scan_qr),
                showArrow = false
            ) {
                onSettingEntryClick.invoke(SettingEntry.ScanQR)
            }

            SettingButton(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                icon = painterResource(id = R.drawable.ic_my_qr),
                text = stringResource(R.string.my_qr),
                showArrow = false
            ) {
                onShowQrDialog()
            }
        }

        SettingList(
            items = listOf(
                SettingListItem.Section(UiText.DynamicString("More")),
                SettingListItem.Setting(SettingEntry.Help),
            ),
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            onSettingEntryClick = onSettingEntryClick
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth(), Alignment.Center
        ) {

            TextButton(
                onClick = {
                    onLogoutIntent(
                        LogoutIntent.ToggleLogoutDialog(
                            isShown = true
                        )
                    )
                },
            ) {
                Text(
                    text = stringResource(R.string.log_out), style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight(600),
                        color = Color(0xFFFF552F),
                        textAlign = TextAlign.Center,
                        textDecoration = TextDecoration.Underline,
                    )
                )
            }

        }

        Spacer(Modifier.height(32.dp))
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileToolBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.your_profile), style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFFFFFF),
                )
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier
            .wrapContentHeight()
            .padding(top = 16.dp)
    )
}

@Preview
@Composable
fun ProfileScreen_Preview() {
    ScreenPreview {
        ProfileScreen_Ui(
            modifier = Modifier.fillMaxSize(),
            state = ProfileScreenState(
                profile = ProfileUi.mock()
            )
        )
    }
}
package by.alexandr7035.banking.ui.feature_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import by.alexandr7035.banking.ui.components.SettingButton
import by.alexandr7035.banking.ui.components.header.ScreenHeader
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.extensions.showToast
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel(), onLogoutClick: () -> Unit = {}, onSettingEntry: (entry: SettingEntry) -> Unit = {}
) {
    val state = viewModel.state.collectAsStateWithLifecycle().value
    val context = LocalContext.current

    BoxWithConstraints() {
        ProfileScreen_Ui(modifier = Modifier
            .width(maxWidth)
            .height(maxHeight), onLogOutClick = {
            onLogoutClick.invoke()
        }, onSettingEntryClick = {
            context.showToast("TODO")
            onSettingEntry.invoke(it)
        }, state = state
        )

        LaunchedEffect(Unit) {
            viewModel.emitIntent(ProfileScreenIntent.LoadScreen)
        }
    }
}

@Composable
private fun ProfileScreen_Ui(
    modifier: Modifier, state: ProfileScreenState, onLogOutClick: () -> Unit = {}, onSettingEntryClick: (entry: SettingEntry) -> Unit = {}
) {
    Column(
        modifier = modifier.then(
            Modifier.verticalScroll(rememberScrollState())
        )
    ) {
        ScreenHeader(toolbar = { ProfileToolBar() }) {
            ProfileCard(profile = state.profile, isLoading = state.isLoading)
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .wrapContentHeight()
                .padding(
                    start = 24.dp, end = 24.dp, top = 24.dp, bottom = 32.dp
                ),
        ) {

            Row(
                modifier = Modifier
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
                    onSettingEntryClick.invoke(SettingEntry.MyQR)
                }
            }

            Text(
                text = stringResource(R.string.account), style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333),
                ), modifier = Modifier.padding(top = 8.dp)
            )

            SettingButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(id = R.drawable.ic_profile_filled),
                text = stringResource(R.string.change_personal_profile)
            ) {
                onSettingEntryClick.invoke(SettingEntry.ChangeProfile)
            }

            SettingButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(id = R.drawable.ic_email_filled),
                text = stringResource(R.string.change_email_address)
            ) {
                onSettingEntryClick.invoke(SettingEntry.ChangeEmail)
            }

            SettingButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(id = R.drawable.ic_lock_filled),
                text = stringResource(R.string.change_password)
            ) {
                onSettingEntryClick.invoke(SettingEntry.ChangePassword)
            }

            Text(
                text = stringResource(R.string.more_settings), style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333),
                ), modifier = Modifier.padding(top = 8.dp)
            )

            SettingButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(id = R.drawable.ic_lock_filled_variant),
                text = stringResource(R.string.account_security)
            ) {
                onSettingEntryClick.invoke(SettingEntry.AccountSecurity)
            }

            SettingButton(
                modifier = Modifier.fillMaxWidth(),
                icon = painterResource(id = R.drawable.ic_help),
                text = stringResource(R.string.help_and_privacy)
            ) {
                onSettingEntryClick.invoke(SettingEntry.Help)
            }

            Box(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(), Alignment.Center
            ) {

                TextButton(
                    onClick = {
                        onLogOutClick.invoke()
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


        }
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
        }, colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Transparent), modifier = Modifier.wrapContentHeight()
    )
}

@Preview
@Composable
fun ProfileScreen_Preview() {
    ScreenPreview {
        ProfileScreen_Ui(
            modifier = Modifier.fillMaxSize(), state = ProfileScreenState()
        )
    }
}
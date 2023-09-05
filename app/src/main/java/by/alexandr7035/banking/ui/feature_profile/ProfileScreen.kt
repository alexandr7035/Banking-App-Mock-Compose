package by.alexandr7035.banking.ui.feature_profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.SettingButton
import by.alexandr7035.banking.ui.core.ScreenPreview
import by.alexandr7035.banking.ui.extensions.showToast
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun ProfileScreen(
    onLogOut: () -> Unit = {}
) {
    BoxWithConstraints() {
        ProfileScreen_Ui(
            modifier = Modifier.width(maxWidth).height(maxHeight),
            onLogOut = onLogOut
        )
    }
}

@Composable
private fun ProfileScreen_Ui(
    modifier: Modifier,
    onLogOut: () -> Unit = {}
) {
    val context = LocalContext.current

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.then(
            Modifier
                .padding(
                    horizontal = 24.dp,
                    vertical = 32.dp
                )
                .verticalScroll(rememberScrollState()),
        )
    ) {

        Text(
            text = "Your Profile",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight(600),
//                color = Color(0xFFFFFFFF),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingButton(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.ic_scan_qr),
                text = "Scan QR",
                showArrow = false
            ) {
                context.showToast("TODO")
            }

            SettingButton(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.ic_my_qr),
                text = "My QR",
                showArrow = false
            ) {
                context.showToast("TODO")
            }
        }

        Text(
            text = "Account",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF333333),
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        SettingButton(
            modifier = Modifier.fillMaxWidth(),
            icon = painterResource(id = R.drawable.ic_profile_filled),
            text = "Change Personal Profile"
        ) {
            context.showToast("TODO")
        }

        SettingButton(
            modifier = Modifier.fillMaxWidth(),
            icon = painterResource(id = R.drawable.ic_email_filled),
            text = "Change Email Address"
        ) {
            context.showToast("TODO")
        }

        SettingButton(
            modifier = Modifier.fillMaxWidth(),
            icon = painterResource(id = R.drawable.ic_lock_filled),
            text = "Change Password"
        ) {
            context.showToast("TODO")
        }

        Text(
            text = "More Settings",
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = primaryFontFamily,
                fontWeight = FontWeight(500),
                color = Color(0xFF333333),
            ),
            modifier = Modifier.padding(top = 8.dp)
        )

        SettingButton(
            modifier = Modifier.fillMaxWidth(),
            icon = painterResource(id = R.drawable.ic_lock_filled_variant),
            text = "Account Security"
        ) {
            context.showToast("TODO")
        }

        SettingButton(
            modifier = Modifier.fillMaxWidth(),
            icon = painterResource(id = R.drawable.ic_help),
            text = "Help and Privacy"
        ) {
            context.showToast("TODO")
        }

        TextButton(onClick = {
            context.showToast("TODO")
            onLogOut.invoke()
        }) {
            Text(
                text = "Log out",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFFFF552F),
                    textAlign = TextAlign.Center,
                    textDecoration = TextDecoration.Underline,
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

    }
}

@Preview
@Composable
fun ProfileScreen_Preview() {
    ScreenPreview {
        ProfileScreen_Ui(Modifier.fillMaxSize())
    }
}
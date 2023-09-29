package by.alexandr7035.banking.ui.feature_profile.logout_dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun LogoutDialog(onDismiss: (wantLogout: Boolean) -> Unit = {}) {
    Box(
        contentAlignment = Alignment.TopEnd, modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .background(
                    shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.background
                )
                .verticalScroll(rememberScrollState())
                .padding(24.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Image(
                painter = painterResource(R.drawable.img_logout),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Want to Logout ?", style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF333333),
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You will back to early app if you click the logout button", style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF999999),
                    textAlign = TextAlign.Center,
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            PrimaryButton(
                onClick = { onDismiss.invoke(true) },
                modifier = Modifier.fillMaxWidth(),
                text = "Logout Now"
            )
        }

        IconButton(
            onClick = {
                onDismiss.invoke(false)
            },
            modifier = Modifier
                .size(40.dp)
                .offset(y = -20.dp, x = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_close), contentDescription = null, modifier = Modifier
                    .size(40.dp)
                    .shadow(
                        elevation = 4.dp, shape = CircleShape
                    )
            )

        }
    }

}

@Composable
@Preview
fun LogoutDialog_Preview() {
    BankingAppTheme() {
        Surface(color = Color.LightGray) {
            LogoutDialog({})
        }
    }
}
package by.alexandr7035.banking.ui.feature_cards.screen_card_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.PrimaryButton
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.theme.primaryFontFamily


@Composable
fun ConfirmDeleteCardDialog(
    onDismiss: () -> Unit,
    onConfirmDelete: () -> Unit,
) {
    Dialog(onDismissRequest = {
        onDismiss.invoke()
    }) {
        Box(
            Modifier
                .size(100.dp)
                .background(Color.Gray)
        )

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
                text = "Want to remove Card?", style = TextStyle(
                    fontSize = 24.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF333333),
                    textAlign = TextAlign.Center
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "You can add it again later if you need", style = TextStyle(
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
                onClick = {
                    onDismiss.invoke()
                    onConfirmDelete.invoke()
                },
                modifier = Modifier.fillMaxWidth(),
                text = "Remove Card"
            )
        }
    }
}

@Composable
@Preview
private fun ConfirmDeleteCardDialog_Preview() {
    ScreenPreview {
        ConfirmDeleteCardDialog(onDismiss = {}, onConfirmDelete = {})
    }
}
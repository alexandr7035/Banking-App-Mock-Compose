package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.alexandr7035.banking.ui.theme.BankingAppTheme

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier.then(Modifier.wrapContentHeight()),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = RoundedCornerShape(30.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}


@Composable
fun SecondaryButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
) {
    Button(
        onClick = onClick,
        modifier = modifier.then(Modifier.wrapContentHeight()),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(30.dp),
        contentPadding = PaddingValues(16.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun TextBtn(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.then(Modifier.wrapContentHeight()),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Preview
@Composable
fun PrimaryButton_Preview() {
    BankingAppTheme() {
        Surface() {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PrimaryButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    text = "Click me"
                )

                SecondaryButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    text = "Click me"
                )

                TextBtn(
                    onClick = {},
                    modifier = Modifier.wrapContentSize(),
                    text = "Click me"
                )
            }

        }
    }
}
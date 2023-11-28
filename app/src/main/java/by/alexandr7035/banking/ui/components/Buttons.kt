package by.alexandr7035.banking.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.cards.PrimaryCard
import by.alexandr7035.banking.ui.components.modifiers.dashedBorder
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    isEnabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier.then(Modifier.wrapContentHeight()),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = RoundedCornerShape(30.dp),
        contentPadding = PaddingValues(16.dp),
        enabled = isEnabled
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
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
            style = MaterialTheme.typography.titleSmall
        )
    }
}

@Composable
fun TextBtn(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    color: Color = MaterialTheme.colorScheme.primary
) {
    TextButton(
        onClick = onClick,
        modifier = modifier.then(Modifier.wrapContentHeight()),
        contentPadding = PaddingValues(vertical = 0.dp, horizontal = 16.dp),
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.SemiBold,
            color = color,
        )
    }
}

@Composable
fun MenuButton(
    modifier: Modifier,
    icon: Painter,
    text: String,
    showArrow: Boolean = true,
    iconTint: Color? = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit,
) {
    PrimaryCard(
        modifier = modifier,
        onClick = onClick,
        paddingValues = PaddingValues(16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = Color(0xFFF1EDFF),
                        shape = CircleShape
                    )
                    .padding(8.dp)
            ) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    colorFilter = iconTint?.let {  ColorFilter.tint(it) }
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = text,
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF666666),
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            if (showArrow) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = R.drawable.ic_arrow),
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun DashedButton(
    onClick: () -> Unit,
    modifier: Modifier,
    text: String,
    icon: Painter? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier.then(
            Modifier.dashedBorder(
                strokeWidth = 1.5.dp,
                color = MaterialTheme.colorScheme.primary,
                cornerRadiusDp = 10.dp
            )
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
        ),
        shape = RoundedCornerShape(10.dp),
        contentPadding = PaddingValues(16.dp),
    ) {

        Row(verticalAlignment = Alignment.CenterVertically) {
            if (icon != null) {
                Image(
                    painter = icon,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .size(24.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Text(
                text = text,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight.SemiBold,
                )
            )
        }
    }
}


@Preview
@Composable
fun Buttons_Preview() {
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

                DashedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    text = "Add Card"
                )

                DashedButton(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(),
                    icon = painterResource(id = R.drawable.ic_plus),
                    text = "Add Card"
                )

                Box(
                    Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .background(Color.White)
                        .padding(vertical = 56.dp)
                )
                {
                    MenuButton(
                        modifier = Modifier.fillMaxWidth(),
                        icon = painterResource(id = R.drawable.ic_profile_filled),
                        text = "Change Personal Profile",
                        onClick = {}
                    )
                }
            }

        }
    }
}
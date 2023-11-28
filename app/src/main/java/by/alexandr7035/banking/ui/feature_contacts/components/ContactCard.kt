package by.alexandr7035.banking.ui.feature_contacts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.images.debugPlaceholder
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import by.alexandr7035.banking.ui.theme.BankingAppTheme
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactCard(
    modifier: Modifier = Modifier,
    contact: ContactUi,
    isSelected: Boolean = false,
    onCLick: (contactId: Long) -> Unit = {}
) {
    val shape = RoundedCornerShape(10.dp)

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFEFEFEF)
        ),
        shape = shape,
        onClick = { onCLick.invoke(contact.id) },
    ) {
        val selectedBorder = if (isSelected) {
            Modifier.border(
                brush = Brush.horizontalGradient(
                    listOf(
                        Color(0xFF797979),
                        Color(0x808F8F8F),
                        Color(0x4DC7C7C7),
                    )
                ),
//                color = MaterialTheme.colorScheme.primary,
                width = 4.dp,
                shape = shape
            )
        } else {
            Modifier
        }

        Row(
            modifier = modifier
                .then(selectedBorder)
                .padding(16.dp)
        ) {
            val imageReq = ImageRequest.Builder(LocalContext.current)
                .data(contact.profilePictureUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build()

            AsyncImage(
                model = imageReq,
                placeholder = debugPlaceholder(R.drawable.ic_profile_filled),
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFBCC3FF)),
                contentDescription = null,
            )

            Spacer(Modifier.width(16.dp))

            Column(
                Modifier
                    .weight(1f, fill = true)
            ) {
                Text(
                    text = contact.name,
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF333333),
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = contact.cardNumber,
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontFamily = primaryFontFamily,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF100D40),
                    ),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
@Preview
fun ContactCard_Preview() {
    BankingAppTheme() {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            ContactCard(
                contact = ContactUi.mock()
            )

            ContactCard(
                contact = ContactUi.mock(),
                isSelected = true
            )
        }
    }
}
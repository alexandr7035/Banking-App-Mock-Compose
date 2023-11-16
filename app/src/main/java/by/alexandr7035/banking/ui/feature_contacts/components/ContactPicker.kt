package by.alexandr7035.banking.ui.feature_contacts.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.ui.components.ScreenPreview
import by.alexandr7035.banking.ui.components.debug.debugPlaceholder
import by.alexandr7035.banking.ui.components.decoration.SkeletonShape
import by.alexandr7035.banking.ui.core.extensions.splitStringWithDivider
import by.alexandr7035.banking.ui.feature_contacts.model.ContactUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun ContactPicker(
    modifier: Modifier = Modifier,
    selectedContact: ContactUi?,
    isLoading: Boolean,
    onChooseContactClick: () -> Unit = {}
) {

    when {
        isLoading -> ContactSelector_Skeleton(modifier)
        selectedContact != null -> ContactPicker_Selected(modifier, selectedContact, onChooseContactClick)
        selectedContact == null -> ContactPicker_NotSelected(modifier, onChooseContactClick)
    }

}

@Composable
private fun ContactPicker_Selected(
    modifier: Modifier,
    selectedContact: ContactUi,
    onChooseContactClick: () -> Unit = {}
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val imageReq = ImageRequest.Builder(LocalContext.current)
            .data(selectedContact.profilePictureUrl)
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
                text = selectedContact.name,
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
                text = selectedContact.cardNumber.splitStringWithDivider(),
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

        TextButton(onClick = { onChooseContactClick() }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Change contact",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
private fun ContactPicker_NotSelected(
    modifier: Modifier,
    onChooseContactClick: () -> Unit
) {
    Row(modifier = modifier) {
        TextButton(onClick = { onChooseContactClick() }) {
            Text(
                text = stringResource(R.string.choose_contact), style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(600),
                    color = Color(0xFF100D40),
                )
            )

            Spacer(Modifier.width(6.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_down_arrrow),
                contentDescription = "Drop down"
            )
        }
    }
}

@Composable
private fun ContactSelector_Skeleton(
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        SkeletonShape(
            modifier = Modifier.size(48.dp),
            shape = CircleShape
        )

        Spacer(Modifier.width(16.dp))

        Column(verticalArrangement = Arrangement.SpaceBetween) {
            SkeletonShape(modifier = Modifier
                .width(100.dp)
                .height(14.dp))
            Spacer(Modifier.height(8.dp))
            SkeletonShape(modifier = Modifier
                .width(120.dp)
                .height(16.dp))
        }
    }
}

@Composable
@Preview
fun ContactSelector_Preview() {
    ScreenPreview {
        Column(
            Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ContactPicker(
                modifier = Modifier.fillMaxWidth(),
                selectedContact = null,
                isLoading = false
            )

            ContactPicker(
                modifier = Modifier.fillMaxWidth(),
                selectedContact = ContactUi.mock(),
                isLoading = false
            )

            ContactPicker(
                modifier = Modifier.fillMaxWidth(),
                selectedContact = null,
                isLoading = true
            )
        }
    }
}
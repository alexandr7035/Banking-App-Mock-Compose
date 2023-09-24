package by.alexandr7035.banking.ui.feature_profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R
import by.alexandr7035.banking.data.profile.Profile
import by.alexandr7035.banking.ui.components.debug.debugPlaceholder
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest

@Composable
fun ProfileCard(profile: Profile) {
    val shape = RoundedCornerShape(size = 10.dp)

    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
            .shadow(
                elevation = 32.dp,
                spotColor = Color.Gray,
                ambientColor = Color.Gray,
                shape = shape,
            )
            .background(
                color = Color(0xFFFFFFFF), shape = shape
            )
            .padding(16.dp)
            .height(intrinsicSize = IntrinsicSize.Max)
            .fillMaxWidth()
//            .wrapContentHeight()

    ) {
        // Avatar container
        Box(modifier = Modifier.size(48.dp), contentAlignment = Alignment.BottomEnd) {
            val imageReq = ImageRequest.Builder(LocalContext.current)
                .data(profile.profilePicUrl)
                .decoderFactory(SvgDecoder.Factory())
                .crossfade(true)
                .build()

            AsyncImage(
                model = imageReq,
                placeholder = debugPlaceholder(R.drawable.ic_profile_filled),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .background(Color(0xFFBCC3FF)),
                contentDescription = null,
            )

            Image(
                painter = painterResource(id = R.drawable.ic_edit_profile),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }

        Spacer(Modifier.width(16.dp))

        Column(
            Modifier
                .padding(vertical = 4.dp)
                .weight(1f, fill = false)
        ) {
            Text(
                text = profile.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 20.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF333333),
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = profile.id,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(400),
                    color = Color(0xFF999999),
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }

        // TODO fill max space
        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xFF100D40), shape = RoundedCornerShape(size = 32.dp))
                .width(63.dp)
                .height(26.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = profile.tier,
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    fontFamily = primaryFontFamily,
                    fontWeight = FontWeight(500),
                    color = Color(0xFF100D40),
                )
            )
        }
    }
}

@Preview
@Composable
fun ProfileCard_Preview() {
    ProfileCard(Profile.mock())
}
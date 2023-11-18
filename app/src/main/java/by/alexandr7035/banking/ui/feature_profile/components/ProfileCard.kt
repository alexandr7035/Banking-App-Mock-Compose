package by.alexandr7035.banking.ui.feature_profile.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import by.alexandr7035.banking.ui.components.debug.debugPlaceholder
import by.alexandr7035.banking.ui.feature_profile.model.ProfileUi
import by.alexandr7035.banking.ui.theme.primaryFontFamily
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.valentinilk.shimmer.shimmer

@Composable
fun ProfileCard(
    profile: ProfileUi?,
    isLoading: Boolean
) {
    val shape = RoundedCornerShape(size = 10.dp)

    Box(
        modifier = Modifier
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
    ) {
        when {
            profile != null -> ProfileCard_Content(profile = profile)
            isLoading -> ProfileCard_Skeleton()
        }
    }
}

@Composable
private fun ProfileCard_Content(profile: ProfileUi) {
    Row(verticalAlignment = Alignment.CenterVertically) {
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

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            Modifier
                .padding(vertical = 4.dp)
                .weight(1f, fill = true)
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

        Spacer(modifier = Modifier.width(16.dp))

        Box(
            modifier = Modifier
                .border(width = 1.dp, color = Color(0xFF100D40), shape = RoundedCornerShape(size = 32.dp))
                .padding(vertical = 6.dp, horizontal = 18.dp),
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

@Composable
private fun ProfileCard_Skeleton() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .shimmer()
                .background(Color.LightGray, CircleShape)
        )

        Spacer(Modifier.width(16.dp))

        Column(
            Modifier
                .padding(vertical = 4.dp)
                .weight(1f, fill = false)
        ) {

            Box(
                Modifier
                    .height(16.dp)
                    .width(160.dp)
                    .shimmer()
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.height(8.dp))

            Box(
                Modifier
                    .height(16.dp)
                    .width(100.dp)
                    .shimmer()
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
            )
        }
    }
}

@Preview
@Composable
fun ProfileCard_Preview() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        ProfileCard(ProfileUi.mock(), false)
        ProfileCard(null, true)
    }
}
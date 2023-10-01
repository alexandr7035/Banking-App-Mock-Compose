package by.alexandr7035.banking.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import by.alexandr7035.banking.R

// FIXME cant use google fonts due to issue with drawing when default font replaced with google's
// FIXME the issue happens with IntrinsicSize calculation
//val primaryFontFamily = FontFamily(
//    Font(
//        googleFont = PoppinsFont,
//        fontProvider = googleFontProvider,
//        weight = FontWeight.SemiBold,
//        style = FontStyle.Normal
//    )
//)

val primaryFontFamily = FontFamily(
    androidx.compose.ui.text.font.Font(
        R.font.poppins,
        FontWeight.Normal
    ),
    androidx.compose.ui.text.font.Font(
        R.font.poppins_medium,
        FontWeight.Medium
    ),
    androidx.compose.ui.text.font.Font(
        R.font.poppins_semibold,
        FontWeight.SemiBold
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    titleSmall = TextStyle(
        fontFamily = primaryFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.SemiBold
    ),

    titleMedium = TextStyle(
        fontFamily = primaryFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold
    ),
    titleLarge = TextStyle(
        fontFamily = primaryFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold
    ),
    bodyMedium = TextStyle(
        fontFamily = primaryFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Gray30,
        lineHeight = 24.sp
    ),

    labelSmall = TextStyle(
        fontFamily = primaryFontFamily,
        fontSize = 12.sp,
        color = Gray50,
        fontWeight = FontWeight.Normal
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)
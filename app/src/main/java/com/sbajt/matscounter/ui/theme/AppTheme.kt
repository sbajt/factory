package com.sbajt.matscounter.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val darkColors = ColorScheme(
    primary = Black1,
    onPrimary = Black2,
    secondary = Gray,
    onSecondary = Gray1,
    error = Error,
    onError = Error1,
    background = Black,
    onBackground = Black,
    surface = Black,
    onSurface = Black
)

val lightColors = ColorScheme(
    primary = White1,
    onPrimary = White2,
    secondary = Gray,
    onSecondary = Gray1,
    error = Error,
    onError = Error1,
    background = White,
    onBackground = White,
    surface = White,
    onSurface = White
)

val typography = TypographyScheme(
    titleTextNormal = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
    ),
    titleTextLarge = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
    ),
    subtitleTextNormal = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Normal,
    ),
    subtitleTextLarge = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
    ),
    bodyTextNormal = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal,
    ),
    bodyTextLarge = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    )
)

val shape = ShapeScheme(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50)
)

val size = SizeScheme(
    small = 8.dp,
    medium = 12.dp,
    large = 16.dp
)

@Composable
fun MatsCounterTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> darkColors
        else -> lightColors
    }
    CompositionLocalProvider(
        localColorScheme provides colorScheme,
        localTypographyScheme provides typography,
        localShapeScheme provides shape,
        localSizeScheme provides size,
        content = content,
    )
}

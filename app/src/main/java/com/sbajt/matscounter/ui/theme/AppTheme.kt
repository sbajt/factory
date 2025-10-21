package com.sbajt.matscounter.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val lightColors = ColorScheme(
    primary = Black,
    onPrimary = Black1,
    secondary = Purple,
    onSecondary = Purple1,
    accent = Accent,
    error = Error,
    onError = Error1,
    background = White,
    onBackground = White1,
    surface = White1,
    onSurface = White2,
    fadingEdge = White
)

val darkColors = lightColors.copy(
    primary = White,
    onPrimary = White1,
    background = Black,
    onBackground = Black1,
    surface = Black1,
    onSurface = Black2,
    fadingEdge = Black,
)

val typography = TypographyScheme(
    titleTextNormal = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = TextUnit(13.sp.value, TextUnitType.Sp),
        fontWeight = FontWeight.Bold,
    ),
    titleTextLarge = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = TextUnit(15.sp.value, TextUnitType.Sp),
        fontWeight = FontWeight.Bold,
    ),
    subtitleTextNormal = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = TextUnit(11.sp.value, TextUnitType.Sp),
        fontWeight = FontWeight.Normal,
    ),
    subtitleTextLarge = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = TextUnit(13.sp.value, TextUnitType.Sp),
        fontWeight = FontWeight.Normal,
    ),
    bodyTextNormal = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = TextUnit(10.sp.value, TextUnitType.Sp),
        fontWeight = FontWeight.Normal,
    ),
    bodyTextLarge = TextStyle(
        fontFamily = MatsCounterFontFamily,
        fontSize = TextUnit(12.sp.value, TextUnitType.Sp),
        fontWeight = FontWeight.Normal,
    )
)

val shape = ShapeScheme(
    container = RoundedCornerShape(12.dp),
    button = RoundedCornerShape(50)
)

val size = SizeScheme(
    small = 6.dp,
    medium = 8.dp,
    large = 16.dp,
    iconSmall = 28.dp,
    icon = 80.dp,
    tab = 2.dp,
    actionBarAction = 32.dp,
    gridCell = 90.dp,
    contentPadding = 16.dp,
    fadingEdge = 20.dp,
)

@Composable
fun FactoryTheme(
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

object FactoryTheme {
    val colors: ColorScheme
        @Composable get() = localColorScheme.current

    val typography: TypographyScheme
        @Composable get() = localTypographyScheme.current

    val shape: ShapeScheme
        @Composable get() = localShapeScheme.current

    val dimensions: SizeScheme
        @Composable get() = localSizeScheme.current
}

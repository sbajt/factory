package com.sbajt.matscounter.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class ColorScheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val error: Color,
    val onError: Color,
    val background: Color,
    val onBackground: Color,
    val surface: Color,
    val onSurface: Color
)

data class TypographyScheme(
    val titleTextNormal: TextStyle,
    val titleTextLarge: TextStyle,
    val subtitleTextNormal: TextStyle,
    val subtitleTextLarge: TextStyle,
    val bodyTextNormal: TextStyle,
    val bodyTextLarge: TextStyle,

    )

data class SizeScheme(
    val small: Dp,
    val medium: Dp,
    val large: Dp,
)

data class ShapeScheme(
    val container: Shape,
    val button: Shape,
)

val localColorScheme = staticCompositionLocalOf {
    ColorScheme(
        primary = Color(0xFF6200EE),
        onPrimary = Color(0xFFFFFFFF),
        secondary = Color(0xFF03DAC6),
        onSecondary = Color(0xFF000000),
        error = Color(0xFFB00020),
        onError = Color(0xFFFFFFFF),
        background = Color(0xFFFFFFFF),
        onBackground = Color(0xFF000000),
        surface = Color(0xFFFFFFFF),
        onSurface = Color(0xFF000000)
    )
}

val localTypographyScheme = staticCompositionLocalOf {
    TypographyScheme(
        titleTextNormal = TextStyle.Default,
        titleTextLarge = TextStyle.Default,
        subtitleTextNormal = TextStyle.Default,
        subtitleTextLarge = TextStyle.Default,
        bodyTextNormal = TextStyle.Default,
        bodyTextLarge = TextStyle.Default
    )
}

val localSizeScheme = staticCompositionLocalOf {
    SizeScheme(
        small = Dp.Unspecified,
        medium = Dp.Unspecified,
        large = Dp.Unspecified
    )
}

val localShapeScheme = staticCompositionLocalOf {
    ShapeScheme(
        container = RectangleShape,
        button = RectangleShape,
    )
}

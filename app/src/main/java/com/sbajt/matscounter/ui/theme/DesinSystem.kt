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
    val accent: Color,
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
    val iconSmall: Dp,
    val icon: Dp,
    val contentPadding: Dp,
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingLarge: Dp,
)

data class ShapeScheme(
    val container: Shape,
    val button: Shape,
    val tabIndicator: Shape = RectangleShape,
)

val localColorScheme = staticCompositionLocalOf {
    ColorScheme(
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        error = Color.Unspecified,
        accent = Color.Unspecified,
        onError = Color.Unspecified,
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface =  Color.Unspecified,
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
        large = Dp.Unspecified,
        iconSmall = Dp.Unspecified,
        icon = Dp.Unspecified,
        contentPadding = Dp.Unspecified,
        paddingSmall = Dp.Unspecified,
        paddingMedium = Dp.Unspecified,
        paddingLarge = Dp.Unspecified,
    )
}

val localShapeScheme = staticCompositionLocalOf {
    ShapeScheme(
        container = RectangleShape,
        button = RectangleShape,
    )
}

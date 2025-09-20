package org.moa.moa.presentation.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val transparent = Color.Transparent
val unspecified = Color.Unspecified

val MAIN = Color(0xFFFFC941)
val PINK = Color(0xFFFF6198)
val ORANGE = Color(0xFFFB582D)
val RED = Color(0xFFE3224E)
val IVORY = Color(0xFFFEE9AA)
val YELLOW = Color(0xFFF9D801)
val BROWN = Color(0xFF76413D)
val BLACK = Color(0xFF141414)
val GRAY1 = Color(0xFF555555)
val GRAY2 = Color(0xFFAFAFAF)
val GRAY3 = Color(0xFFD9D9D9)
val GRAY4 = Color(0xFFECECEC)
val GRAY5 = Color(0xFFF3F3F3)
val GRAY6 = Color(0xFFF7F7F7)
val WHITE = Color(0xFFFFFFFF)

val IVORY2 = Color(0xFFEBEBEB)
val IVORY3 = Color(0xFFFFFBF4)
val IVORY4 = Color(0xFFFFF2CC)
val GRAY7 = Color(0xFF474747)

@Composable
fun MOAColorScheme() = ColorScheme(
    primary = MAIN,
    onPrimary = BLACK,
    primaryContainer = WHITE,
    onPrimaryContainer = BLACK,
    inversePrimary = WHITE,
    secondary = WHITE,
    onSecondary = BLACK,
    secondaryContainer = WHITE,
    onSecondaryContainer = BLACK,
    tertiary = WHITE,
    onTertiary = BLACK,
    tertiaryContainer = WHITE,
    onTertiaryContainer = BLACK,
    background = IVORY3,
    onBackground = BLACK,
    surface = WHITE,
    onSurface = BLACK,
    surfaceVariant = WHITE,
    onSurfaceVariant = BLACK,
    surfaceTint = WHITE,
    inverseSurface = WHITE,
    inverseOnSurface = BLACK,
    error = WHITE,
    onError = BLACK,
    errorContainer = WHITE,
    onErrorContainer = BLACK,
    outline = WHITE,
    outlineVariant = WHITE,
    scrim = WHITE,
    surfaceBright = WHITE,
    surfaceDim = WHITE,
    surfaceContainer = WHITE,
    surfaceContainerHigh = WHITE,
    surfaceContainerHighest = WHITE,
    surfaceContainerLow = WHITE,
    surfaceContainerLowest = WHITE,
)
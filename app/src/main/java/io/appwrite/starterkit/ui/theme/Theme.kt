package io.appwrite.starterkit.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Light color scheme
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6650a4), // Purple40
    secondary = Color(0xFF625b71), // PurpleGrey40
    tertiary = Color(0xFF7D5260) // Pink40
)

// Typography
private val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

/**
 * A custom theme composable for the Appwrite Starter Kit application.
 *
 * This function sets the app's overall typography, color scheme, and material theme,
 * providing consistent styling throughout the app. The `content` composable
 * is wrapped within the custom [MaterialTheme].
 *
 * @param content The composable content to be styled with the Appwrite Starter Kit theme.
 */
@Composable
fun AppwriteStarterKitTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        content = content,
        typography = Typography,
        colorScheme = LightColorScheme,
    )
}
package io.appwrite.starterkit.ui.components

import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.min

// Background color for gradients, blur, etc.
val checkeredBackgroundColor = Color(0xFFFAFAFB)

// Max height factor for  background.
const val heightConstraintFactor = 0.5f

/**
 * A custom view modifier that adds a checkered background pattern with a gradient effect
 * and a radial gradient overlay. The checkered pattern consists of gray vertical and horizontal lines
 * drawn over the view's background. The modifier also includes a mask and a radial gradient
 * to create a layered visual effect.
 */
fun Modifier.addCheckeredBackground() = this.drawBehind { drawCheckeredBackground() }

/**
 * Draws a checkered background pattern on the canvas with vertical and horizontal grid lines.
 * Also applies linear and radial gradient overlays for additional visual effects.
 */
fun DrawScope.drawCheckeredBackground() {
    val lineThickness = 0.75f
    val gridSize = min(size.width * 0.1f, 64.dp.toPx())
    val heightConstraint = size.height * heightConstraintFactor

    // Draw vertical lines
    for (x in generateSequence(0f) { it + gridSize }.takeWhile { it <= size.width }) {
        drawRect(
            color = Color.Gray.copy(alpha = 0.3f),
            topLeft = Offset(x, 0f),
            size = Size(lineThickness, heightConstraint),
            style = Fill
        )
    }

    // Draw horizontal lines
    for (y in generateSequence(0f) { it + gridSize }.takeWhile { it <= heightConstraint }) {
        drawRect(
            color = Color.Gray.copy(alpha = 0.3f),
            topLeft = Offset(0f, y),
            size = Size(size.width, lineThickness),
            style = Fill
        )
    }

    drawRadialGradientOverlay()

    drawLinearGradientOverlay()
}

/**
 * Draws a vertical gradient overlay over the canvas to enhance the checkered background's appearance.
 */
fun DrawScope.drawLinearGradientOverlay() {
    val heightConstraint = size.height * heightConstraintFactor
    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(
                checkeredBackgroundColor,
                checkeredBackgroundColor.copy(alpha = 0.3f),
                checkeredBackgroundColor.copy(alpha = 0.5f),
                checkeredBackgroundColor.copy(alpha = 0.7f),
                checkeredBackgroundColor,
            ), endY = heightConstraint
        ),
    )
}

/**
 * Draws a radial gradient overlay over the canvas to create a smooth blend effect from the center outward.
 */
fun DrawScope.drawRadialGradientOverlay() {
    drawRect(
        brush = Brush.radialGradient(
            colors = listOf(
                checkeredBackgroundColor.copy(alpha = 0f),
                checkeredBackgroundColor.copy(alpha = 0.4f),
                checkeredBackgroundColor.copy(alpha = 0.2f),
                Color.Transparent
            ), center = center, radius = maxOf(size.width, size.height) * 2
        ),
    )
}

@Composable
@Preview(showBackground = true)
@RestrictTo(RestrictTo.Scope.TESTS)
private fun CheckeredBackgroundPreview() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .addCheckeredBackground()
    )
}

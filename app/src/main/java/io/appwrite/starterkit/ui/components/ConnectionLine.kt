package io.appwrite.starterkit.ui.components

import androidx.annotation.RestrictTo
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A view that animates a connection line with a checkmark in the middle. The left and right
 * lines expand and contract based on the `show` state, with a tick appearing after a delay.
 *
 * @param show Controls whether the connection line animation and the tick are visible.
 *
 */
@Composable
fun ConnectionLine(show: Boolean) {
    val tickAlpha by animateFloatAsState(
        targetValue = if (show) 1f else 0f,
        animationSpec = tween(
            durationMillis = if (show) 500 else 50,
            easing = FastOutSlowInEasing
        ), label = "TickAlpha"
    )

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Left line
        Sidelines(left = true, show = show)

        // Tick icon
        Box(
            modifier = Modifier
                .padding(2.dp)
                .size(30.dp)
                .alpha(tickAlpha)
                .clip(CircleShape)
                .background(Color(0x14F02E65))
                .border(width = 1.8.dp, color = Color(0x80F02E65), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Checkmark",
                tint = Color(0xFFFD366E),
                modifier = Modifier.size(15.dp)
            )
        }

        // Right line
        Sidelines(left = false, show = show)
    }
}

/**
 * A composable function that animates horizontal sidelines with a gradient effect.
 *
 * @param left Indicates whether the sideline is on the left side (true) or right side (false).
 * @param show Controls the visibility and animation of the sideline.
 */
@Composable
fun RowScope.Sidelines(
    left: Boolean,
    show: Boolean,
) {
    val delay = if (show) 500 else 0
    val duration = if (show) 1250 else 0

    AnimatedVisibility(
        visible = show,
        enter = slideInHorizontally(
            initialOffsetX = { fullWidth ->
                if (left) fullWidth / 2 else -fullWidth / 2
            },
            animationSpec = tween(durationMillis = duration, delayMillis = delay)
        ) + expandHorizontally(
            expandFrom = Alignment.CenterHorizontally,
            animationSpec = tween(durationMillis = duration, delayMillis = delay)
        ) + fadeIn(
            animationSpec = tween(durationMillis = duration, delayMillis = delay)
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { fullWidth ->
                if (left) fullWidth / 2 else -fullWidth / 2
            },
            animationSpec = tween(durationMillis = duration)
        ) + shrinkHorizontally(
            shrinkTowards = Alignment.CenterHorizontally,
            animationSpec = tween(durationMillis = duration)
        ) + fadeOut(
            animationSpec = tween(delayMillis = delay, durationMillis = duration)
        ),
        modifier = Modifier.weight(1f)
    ) {
        Box(
            modifier = Modifier
                .height(1.5.dp)
                .background(
                    Brush.horizontalGradient(
                        colors = if (!left) {
                            listOf(Color(0xFFF02E65), Color(0x26FE9567))
                        } else {
                            listOf(Color(0x26FE9567), Color(0xFFF02E65))
                        }
                    )
                )
        )
    }
}


@Composable
@Preview(showBackground = true)
@RestrictTo(RestrictTo.Scope.TESTS)
private fun AnimatedConnectionLinePreview() {
    var showConnection by remember { mutableStateOf(false) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // Title
        Button(
            colors = ButtonDefaults.buttonColors().copy(containerColor = Color.Transparent),
            border = BorderStroke(1.dp, Color(0xFFFD366E)),
            onClick = {
                showConnection = !showConnection
            }) {
            Text(
                text = "Connection Line Animation",
                color = Color.Black, fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        ConnectionLine(show = showConnection)
    }
}
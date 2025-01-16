package io.appwrite.starterkit.ui.components

import androidx.annotation.RestrictTo
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.appwrite.starterkit.data.models.Status
import io.appwrite.starterkit.ui.icons.AndroidIcon
import io.appwrite.starterkit.ui.icons.AppwriteIcon
import kotlinx.coroutines.delay

/**
 * A composable function that displays a row containing platform icons and a connection line between them.
 * The connection line indicates the status of the platform connection.
 *
 * @param status A [Status] indicating the current connection status.
 */
@Composable
fun TopPlatformView(status: Status) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .padding(horizontal = 40.dp)
    ) {
        // First Platform Icon
        Box(
            contentAlignment = Alignment.Center
        ) {
            PlatformIcon {
                Icon(
                    tint = Color(0xff3ddc84),
                    modifier = Modifier
                        .width(45.86047.dp)
                        .height(45.86047.dp),
                    painter = rememberVectorPainter(AndroidIcon),
                    contentDescription = "Android Icon"
                )
            }
        }

        // ConnectionLine
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            ConnectionLine(show = status == Status.Success)
        }

        // Second Platform Icon
        Box(
            contentAlignment = Alignment.Center
        ) {
            PlatformIcon {
                Icon(
                    tint = Color(0xffFD366E),
                    modifier = Modifier
                        .width(35.86047.dp)
                        .height(35.86047.dp),
                    painter = rememberVectorPainter(AppwriteIcon),
                    contentDescription = "Appwrite Icon"
                )
            }
        }
    }
}

/**
 * A composable function that displays a stylized platform icon with a customizable content block.
 * The icon is rendered with shadows, rounded corners, and a layered background.
 *
 * @param modifier Modifier for additional customizations like size and padding.
 * @param content A composable lambda that defines the inner content of the icon (e.g., an image or vector asset).
 */
@Composable
fun PlatformIcon(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 9.360000610351562.dp,
                    spotColor = Color(0x08000000),
                    ambientColor = Color(0x08000000)
                )
                .border(
                    width = 1.dp,
                    color = Color(0x0A19191C),
                    shape = RoundedCornerShape(size = 24.dp)
                )
                .width(100.dp)
                .height(100.dp)
                .background(color = Color(0xFFFAFAFD), shape = RoundedCornerShape(size = 24.dp))
        ) {
            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 8.dp,
                        spotColor = Color(0x05000000),
                        ambientColor = Color(0x05000000)
                    )
                    .shadow(
                        elevation = 12.dp,
                        spotColor = Color(0x05000000),
                        ambientColor = Color(0x05000000)
                    )
                    .border(
                        width = 1.dp,
                        color = Color(0xFFFAFAFB),
                        shape = RoundedCornerShape(size = 16.dp)
                    )
                    .width(86.04652.dp)
                    .height(86.04652.dp)
                    .align(Alignment.Center)
                    .background(color = Color.White, shape = RoundedCornerShape(size = 16.dp))
            ) {
                // Content
                Box(
                    modifier = Modifier.align(Alignment.Center),
                    content = content
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
@RestrictTo(RestrictTo.Scope.TESTS)
private fun PlatformIconPreview() {
    val status = remember { mutableStateOf<Status>(Status.Idle) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .addCheckeredBackground(),
    ) {
        TopPlatformView(status = status.value)

        ConnectionStatusView(
            status = status.value,
            onSendPingClick = {
                status.value = Status.Loading
                delay(500)
                status.value = Status.Success
            }
        )
    }
}

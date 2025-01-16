package io.appwrite.starterkit.ui.components

import androidx.annotation.RestrictTo
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.appwrite.starterkit.data.models.Status
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * A view that displays the current connection status and allows the user to send a ping
 * to verify the connection. It shows different messages based on the connection status.
 *
 * @param status The current status of the connection, represented by the [Status].
 * @param onSendPingClick A suspend function that is triggered when the "Send a ping" button is clicked.
 */
@Composable
fun ConnectionStatusView(
    status: Status,
    onSendPingClick: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .animateContentSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AnimatedContent(
            label = "",
            targetState = status,
            transitionSpec = { fadeIn() togetherWith fadeOut() }
        ) { statusValue ->
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                when (statusValue) {
                    Status.Loading -> {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = Color.Gray,
                                strokeWidth = 2.dp
                            )
                            Text(
                                text = "Waiting for connection...",
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontWeight = FontWeight(400),
                                color = Color(0xFF2D2D31)
                            )
                        }
                    }

                    Status.Success -> {
                        // header
                        Text(
                            fontSize = 24.sp,
                            text = "Congratulations!",
                            color = Color(0xFF2D2D31),
                            fontWeight = FontWeight(400),
                        )

                        Text(
                            fontSize = 14.sp,
                            lineHeight = 19.6.sp,
                            color = Color(0xFF56565C),
                            text = "You connected your app successfully.",
                        )
                    }

                    Status.Error, Status.Idle -> {
                        // header
                        Text(
                            text = "Check connection",
                            fontSize = 24.sp,
                            lineHeight = 28.8.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF2D2D31)
                        )
                        Text(
                            text = "Send a ping to verify the connection.",
                            fontSize = 14.sp,
                            lineHeight = 19.6.sp,
                            color = Color(0xFF56565C)
                        )
                    }
                }
            }
        }

        // Ping Button
        AnimatedVisibility(
            exit = fadeOut(),
            enter = fadeIn(),
            modifier = Modifier.padding(top = 24.dp),
            visible = status != Status.Loading,
        ) {
            val haptic = LocalHapticFeedback.current

            Button(
                onClick = {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    coroutineScope.launch { onSendPingClick() }
                },
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFD366E)),
            ) {
                Text(
                    fontSize = 14.sp,
                    color = Color.White,
                    text = "Send a ping",
                    fontWeight = FontWeight.Medium,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
@RestrictTo(RestrictTo.Scope.TESTS)
private fun ConnectionStatusViewPreview() {
    val status = remember { mutableStateOf<Status>(Status.Idle) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding()
    ) {
        ConnectionStatusView(
            status = status.value,
            onSendPingClick = {
                status.value = Status.Loading
                delay(2500)
                status.value = Status.Success
            }
        )
    }
}
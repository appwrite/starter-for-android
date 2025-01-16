package io.appwrite.starterkit.ui.components

import androidx.annotation.RestrictTo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A view that contains a list of informational cards displayed vertically.
 */
@Composable
fun GettingStartedCards() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        GeneralInfoCard(
            title = "Edit your app",
            link = null,
            subtitle = {
                HighlightedText()
            }
        )

        // Second Card: Head to Appwrite Cloud
        GeneralInfoCard(
            title = "Head to Appwrite Cloud",
            link = "https://cloud.appwrite.io",
            subtitle = {
                Text(
                    fontSize = 14.sp,
                    color = Color(0xFF56565C),
                    fontWeight = FontWeight(400),
                    text = "Start managing your project from the Appwrite console",
                )
            }
        )

        // Third Card: Explore docs
        GeneralInfoCard(
            title = "Explore docs",
            link = "https://appwrite.io/docs",
            subtitle = {
                Text(
                    fontSize = 14.sp,
                    color = Color(0xFF56565C),
                    fontWeight = FontWeight(400),
                    text = "Discover the full power of Appwrite by diving into our documentation",
                )
            }
        )
    }
}

/**
 * A reusable card component that displays a title and subtitle with optional link functionality.
 * The card becomes clickable if a link is provided and opens the destination URL when clicked.
 *
 * @param title The title text displayed at the top of the card.
 * @param link An optional URL; if provided, the card becomes clickable.
 * @param subtitle A composable lambda that defines the subtitle content of the card.
 */
@Composable
fun GeneralInfoCard(
    title: String,
    link: String?,
    subtitle: @Composable () -> Unit,
) {
    val indication = ripple(bounded = true)
    val uriHandler = LocalUriHandler.current
    val interactionSource = remember { MutableInteractionSource() }

    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFEDEDF0)),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(
                color = Color.White,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .clickable(
                enabled = link != null,
                indication = indication,
                interactionSource = interactionSource,
            ) {
                uriHandler.openUri(link.toString())
            },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 20.sp,
                        lineHeight = 26.sp,
                        fontWeight = FontWeight(400),
                    )
                )
                if (link != null) {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        tint = Color(0xFFD8D8DB),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp),
                        imageVector = Icons.AutoMirrored.Default.ArrowForward,
                    )
                }
            }
            subtitle()
        }
    }
}

/**
 * A composable function that displays highlighted text with a specific word or phrase styled differently.
 * Useful for drawing attention to specific content in a sentence.
 */
@Composable
fun HighlightedText() {
    val text = buildAnnotatedString {
        append("Edit ")
        withStyle(
            style = SpanStyle(
                background = Color(0xFFEDEDF0),
                fontWeight = FontWeight(500)
            )
        ) { append(" MainActivity.kt ") }
        append(" to get started with building your app")
    }

    Text(
        text = text,
        fontSize = 14.sp,
        color = Color(0xFF56565C),
        fontWeight = FontWeight(400),
    )
}

@Composable
@Preview(showBackground = true)
@RestrictTo(RestrictTo.Scope.TESTS)
private fun GettingStartedCardsPreview() {
    GettingStartedCards()
}

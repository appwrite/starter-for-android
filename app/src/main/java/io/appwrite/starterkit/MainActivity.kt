package io.appwrite.starterkit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import io.appwrite.starterkit.data.models.Status
import io.appwrite.starterkit.data.models.mockProjectInfo
import io.appwrite.starterkit.extensions.copy
import io.appwrite.starterkit.extensions.edgeToEdgeWithStyle
import io.appwrite.starterkit.ui.components.CollapsibleBottomSheet
import io.appwrite.starterkit.ui.components.ConnectionStatusView
import io.appwrite.starterkit.ui.components.GettingStartedCards
import io.appwrite.starterkit.ui.components.TopPlatformView
import io.appwrite.starterkit.ui.components.addCheckeredBackground
import io.appwrite.starterkit.ui.theme.AppwriteStarterKitTheme
import io.appwrite.starterkit.viewmodels.AppwriteViewModel
import kotlinx.coroutines.delay

/**
 * MainActivity serves as the entry point for the application.
 * It configures the system's edge-to-edge settings, splash screen, and initializes the composable layout.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        edgeToEdgeWithStyle()
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent { AppwriteStarter() }
    }
}

/**
 * AppwriteStarter is the root composable function that sets up the main UI layout.
 * It manages the logs, status, and project information using the provided [AppwriteViewModel].
 */
@Composable
fun AppwriteStarter(
    viewModel: AppwriteViewModel = viewModel(),
) {
    val logs by viewModel.logs.collectAsState()
    val status by viewModel.status.collectAsState()

    // data doesn't change, so no `remember`.
    val projectInfo = viewModel.getProjectInfo()

    AppwriteStarterKitTheme {
        Scaffold(bottomBar = {
            CollapsibleBottomSheet(
                logs = logs,
                projectInfo = projectInfo
            )
        }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .addCheckeredBackground()
                    .padding(innerPadding.copy(top = 16.dp, bottom = 0.dp))
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TopPlatformView(
                    status = status
                )

                ConnectionStatusView(status) {
                    viewModel.ping()
                }

                GettingStartedCards()
            }
        }
    }
}

@Preview
@Composable
@RestrictTo(RestrictTo.Scope.TESTS)
private fun AppwriteStarterPreview() {
    val status = remember { mutableStateOf<Status>(Status.Idle) }

    AppwriteStarterKitTheme {
        Scaffold(bottomBar = {
            CollapsibleBottomSheet(
                logs = emptyList(),
                projectInfo = mockProjectInfo
            )
        }) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .addCheckeredBackground()
                    .padding(innerPadding.copy(top = 16.dp, bottom = 0.dp))
                    .windowInsetsPadding(WindowInsets.systemBars)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TopPlatformView(
                    status = status.value
                )

                ConnectionStatusView(status.value) {
                    // simulate a success ping
                    status.value = Status.Loading
                    delay(1000)
                    status.value = Status.Success
                }

                GettingStartedCards()
            }
        }
    }
}
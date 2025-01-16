package io.appwrite.starterkit.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.appwrite.starterkit.data.models.Log
import io.appwrite.starterkit.data.models.ProjectInfo
import io.appwrite.starterkit.data.models.Status
import io.appwrite.starterkit.data.repository.AppwriteRepository
import io.appwrite.starterkit.data.repository.AppwriteRepository.Companion.APPWRITE_PROJECT_ID
import io.appwrite.starterkit.data.repository.AppwriteRepository.Companion.APPWRITE_PROJECT_NAME
import io.appwrite.starterkit.data.repository.AppwriteRepository.Companion.APPWRITE_PUBLIC_ENDPOINT
import io.appwrite.starterkit.data.repository.AppwriteRepository.Companion.APPWRITE_VERSION
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * A ViewModel class that serves as the central hub for managing and storing the state
 * related to Appwrite operations, such as project information, connection status, and logs.
 */
class AppwriteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppwriteRepository.getInstance(application)

    private val _status = MutableStateFlow<Status>(Status.Idle)
    private val _logs = MutableStateFlow<List<Log>>(emptyList())

    val logs: StateFlow<List<Log>> = _logs.asStateFlow()
    val status: StateFlow<Status> = _status.asStateFlow()

    /**
     * Retrieves project information such as version, project ID, endpoint, and project name.
     *
     * @return [ProjectInfo] An object containing project details.
     */
    fun getProjectInfo(): ProjectInfo {
        return ProjectInfo(
            version = APPWRITE_VERSION,
            projectId = APPWRITE_PROJECT_ID,
            endpoint = APPWRITE_PUBLIC_ENDPOINT,
            projectName = APPWRITE_PROJECT_NAME
        )
    }

    /**
     * Executes a ping operation to verify connectivity and logs the result.
     *
     * Updates the [status] to [Status.Loading] during the operation and then updates it
     * based on the success or failure of the ping. Appends the result to [logs].
     */
    suspend fun ping() {
        _status.value = Status.Loading
        val log = repository.fetchPingLog()

        _logs.value += log

        delay(1000)

        _status.value = if (log.status.toIntOrNull() in 200..399) {
            Status.Success
        } else {
            Status.Error
        }
    }
}

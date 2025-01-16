package io.appwrite.starterkit.data.repository

import android.content.Context
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import io.appwrite.starterkit.data.models.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * [AppwriteRepository] is responsible for handling network interactions with the Appwrite server.
 *
 * It provides a helper method to ping the server.
 *
 * **NOTE: This repository will be removed once the Appwrite SDK includes a native `client.ping()` method.**\
 * TODO: remove this repository once sdk has `client.ping()`
 */
class AppwriteRepository private constructor(context: Context) {

    // Appwrite Client and Services
    private val client = Client(context.applicationContext)
        .setProject(APPWRITE_PROJECT_ID)
        .setEndpoint(APPWRITE_PUBLIC_ENDPOINT)

    private val account: Account = Account(client)
    private val databases: Databases = Databases(client)

    /**
     * Pings the Appwrite server.
     * Captures the response or any errors encountered during the request.
     *
     * @return [Log] A log object containing details of the request and response.
     */
    suspend fun fetchPingLog(): Log = withContext(Dispatchers.IO) {
        val url = URL("${client.endpoint}$PING_PATH")
        val connection = (url.openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"

            readTimeout = DEFAULT_TIMEOUT
            connectTimeout = DEFAULT_TIMEOUT

            setRequestProperty("Content-Type", "application/json")
            setRequestProperty("x-appwrite-response-format", APPWRITE_VERSION)
            setRequestProperty("x-appwrite-project", APPWRITE_PROJECT_ID)
        }

        try {
            val statusCode = connection.responseCode
            val response = if (statusCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use(BufferedReader::readText)
            } else {
                "Request failed with status code $statusCode"
            }

            Log(
                date = getCurrentDate(),
                status = statusCode.toString(),
                method = "GET",
                path = PING_PATH,
                response = response
            )
        } catch (e: Exception) {
            Log(
                date = getCurrentDate(),
                status = "Error",
                method = "GET",
                path = PING_PATH,
                response = "Error occurred: ${e.message}"
            )
        } finally {
            connection.disconnect()
        }
    }

    /**
     * Retrieves the current date in the format "MMM dd, HH:mm".
     *
     * @return [String] A formatted date.
     */
    private fun getCurrentDate(): String {
        val formatter = SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
        return formatter.format(Date())
    }

    companion object {
        @Volatile
        private var INSTANCE: AppwriteRepository? = null

        /**
         * Appwrite Server version.
         */
        const val APPWRITE_VERSION = "1.6.0"

        /**
         * Appwrite project id
         */
        const val APPWRITE_PROJECT_ID = "my-project-id"

        /**
         * Appwrite project name
         */
        const val APPWRITE_PROJECT_NAME = "My project"

        /**
         * Appwrite server endpoint url.
         */
        const val APPWRITE_PUBLIC_ENDPOINT = "https://cloud.appwrite.io/v1"

        /**
         * The path to use for ping.
         */
        const val PING_PATH = "/ping"

        /**
         * Default network timeout.
         */
        const val DEFAULT_TIMEOUT = 5_000

        /**
         * Singleton factory method to get the instance of AppwriteRepository.
         * Ensures thread safety
         */
        fun getInstance(context: Context): AppwriteRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: AppwriteRepository(context).also { INSTANCE = it }
            }
        }
    }
}

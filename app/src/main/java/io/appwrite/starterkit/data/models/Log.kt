package io.appwrite.starterkit.data.models

/**
 * A data model for holding log entries.
 */
data class Log(
    val date: String,
    val status: String,
    val method: String,
    val path: String,
    val response: String,
)
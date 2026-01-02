package io.soma.cryptobook.splash.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AppVersionDTO(
    val platform: String,
    @SerialName("min_version") val minVersion: String,
    @SerialName("latest_version") val latestVersion: String,
)

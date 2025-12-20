package io.soma.cryptobook.navigation

import androidx.core.net.toUri
import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import javax.inject.Inject

sealed interface CbScreen : NavKey {
    @Serializable
    data object CoinList : CbScreen

    @Serializable
    data class CoinDetail(val symbol: String) : CbScreen

    @Serializable
    data object Settings : CbScreen

    @Serializable
    data class Error(val reason: String?) : CbScreen
}

class LinkRouter @Inject constructor() {
    fun resolve(link: String): CbScreen {
        val uri = runCatching { link.toUri() }
            .getOrElse { return CbScreen.Error("invalid_url") }
        val segments = uri.pathSegments ?: emptyList()
        if (segments.isEmpty()) return CbScreen.Error("empty_path")
        return when (segments[0]) {
            "coindetail" -> {
                val coinName = segments.getOrNull(1)
                    ?.takeIf { it.isNotBlank() }
                    ?: return CbScreen.Error("missing_coinname")
                CbScreen.CoinDetail(coinName)
            }

            "coinlist" -> CbScreen.CoinList
            "settings" -> CbScreen.Settings
            else -> CbScreen.Error("unmatched_deeplink")
        }
    }
}

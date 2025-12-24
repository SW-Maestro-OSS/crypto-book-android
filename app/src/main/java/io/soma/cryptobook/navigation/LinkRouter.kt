package io.soma.cryptobook.navigation

import androidx.core.net.toUri
import androidx.navigation3.runtime.NavKey
import io.soma.cryptobook.coindetail.presentation.navigation.CoinDetailNavKey
import io.soma.cryptobook.coinlist.presentation.navigation.CoinListNavKey
import io.soma.cryptobook.core.domain.navigation.AppPage
import io.soma.cryptobook.settings.presentation.navigation.SettingsNavKey
import javax.inject.Inject

class LinkRouter @Inject constructor() {
    fun resolve(link: String): NavKey {
        val uri = runCatching { link.toUri() }
            .getOrElse { return CoinListNavKey }
        val segments = uri.pathSegments ?: emptyList()
        if (segments.isEmpty()) return CoinListNavKey
        return when (segments[0]) {
            "coindetail" -> {
                val coinName = segments.getOrNull(1)
                    ?.takeIf { it.isNotBlank() }
                    ?: return CoinListNavKey
                CoinDetailNavKey(coinName)
            }

            "coinlist" -> CoinListNavKey
            "settings" -> SettingsNavKey
            else -> CoinListNavKey
        }
    }

    fun resolve(page: AppPage): NavKey = when (page) {
        is AppPage.CoinList -> CoinListNavKey
        is AppPage.CoinDetail -> CoinDetailNavKey(page.coinName)
        is AppPage.Settings -> SettingsNavKey
    }
}

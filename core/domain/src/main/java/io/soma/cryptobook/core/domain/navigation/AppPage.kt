package io.soma.cryptobook.core.domain.navigation

sealed class AppPage {
    data object CoinList : AppPage()
    data class CoinDetail(val coinName: String) : AppPage()
    data object Settings : AppPage()
}

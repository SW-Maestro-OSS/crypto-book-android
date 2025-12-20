package io.soma.cryptobook.core.domain.navigation

import java.net.URLEncoder

sealed class Destination(private val path: String) {
    data object CoinList : Destination("/coinlist")
    data object Settings : Destination("/settings")

    fun getLinkWith(params: Map<String, String> = emptyMap()): String {
        val base = "https://cryptobook$path"
        if (params.isEmpty()) return base
        val query = params.entries.joinToString("&") { (k, v) -> "$k=${encode(v)}" }
        return "$base?$query"
    }

    private fun encode(value: String): String = URLEncoder.encode(value, Charsets.UTF_8.name())
}

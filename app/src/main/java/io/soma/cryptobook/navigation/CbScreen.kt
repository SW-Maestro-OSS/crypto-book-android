package io.soma.cryptobook.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface CbScreen : NavKey {
    @Serializable
    data object CoinList : CbScreen
    @Serializable
    data class CoinDetail(val symbol: String) : CbScreen
    @Serializable
    data object Settings : CbScreen
}
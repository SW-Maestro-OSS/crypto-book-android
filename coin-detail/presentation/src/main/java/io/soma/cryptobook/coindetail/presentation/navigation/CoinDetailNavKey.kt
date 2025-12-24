package io.soma.cryptobook.coindetail.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data class CoinDetailNavKey(val coinName: String) : NavKey
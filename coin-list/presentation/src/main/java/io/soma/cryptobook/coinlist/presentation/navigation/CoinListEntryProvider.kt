package io.soma.cryptobook.coinlist.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.soma.cryptobook.coinlist.presentation.CoinListRoute

fun EntryProviderScope<NavKey>.coinListEntry() {
    entry<CoinListNavKey> {
        CoinListRoute()
    }
}
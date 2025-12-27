package io.soma.cryptobook.home.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.soma.cryptobook.home.presentation.HomeRoute

fun EntryProviderScope<NavKey>.homeEntry() {
    entry<HomeNavKey> {
        HomeRoute()
    }
}
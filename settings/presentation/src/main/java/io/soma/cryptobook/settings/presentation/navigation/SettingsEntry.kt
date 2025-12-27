package io.soma.cryptobook.settings.presentation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.soma.cryptobook.settings.presentation.SettingsRoute

fun EntryProviderScope<NavKey>.settingsEntry() {
    entry<SettingsNavKey> {
        SettingsRoute()
    }
}

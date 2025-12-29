package io.soma.cryptobook.main.presentation.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import io.soma.cryptobook.home.presentation.navigation.HomeNavKey
import io.soma.cryptobook.settings.presentation.navigation.SettingsNavKey
import io.soma.cryptobook.home.presentation.R as homeR
import io.soma.cryptobook.settings.presentation.R as settingsR

data class TopLevelNavItem(
    val icon: ImageVector,
    @StringRes val iconTextId: Int,
)

val HOME = TopLevelNavItem(
    icon = Icons.Outlined.Home,
    iconTextId = homeR.string.feature_home_title,
)

val SETTINGS = TopLevelNavItem(
    icon = Icons.Outlined.Settings,
    iconTextId = settingsR.string.feature_settings_title,
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    HomeNavKey to HOME,
    SettingsNavKey to SETTINGS,
)

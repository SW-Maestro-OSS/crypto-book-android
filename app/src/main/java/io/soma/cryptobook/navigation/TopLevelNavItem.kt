package io.soma.cryptobook.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import io.soma.cryptobook.coinlist.presentation.R as coinlistR
import io.soma.cryptobook.settings.presentation.R as settingsR


data class TopLevelNavItem(
    val icon: ImageVector,
    @StringRes val iconTextId: Int,
)

val MARKET = TopLevelNavItem(
    icon = Icons.Outlined.Home,
    iconTextId = coinlistR.string.feature_coinlist_title
)

val SETTINGS = TopLevelNavItem(
    icon = Icons.Outlined.Settings,
    iconTextId = settingsR.string.feature_settings_title
)

val TOP_LEVEL_NAV_ITEMS = mapOf(
    CbScreen.CoinList to MARKET,
    CbScreen.Settings to SETTINGS,
)
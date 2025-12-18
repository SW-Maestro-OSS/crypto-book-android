package io.soma.cryptobook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.soma.cryptobook.navigation.CbNavHost
import io.soma.cryptobook.navigation.TOP_LEVEL_NAV_ITEMS
import io.soma.cryptobook.navigation.rememberCbNavigator
import io.soma.cryptobook.ui.theme.CryptoBookTheme

@Composable
fun CryptoBookApp(
    modifier: Modifier = Modifier
) {
    CryptoBookTheme() {
        val navigator = rememberCbNavigator()

        NavigationSuiteScaffold(
            navigationSuiteItems = {
                TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
                    val selected =
                        navKey == navigator.backStack.firstOrNull { it in TOP_LEVEL_NAV_ITEMS.keys }
                    item(
                        selected = selected,
                        icon = { Icon(navItem.icon, contentDescription = null) },
                        label = { Text(stringResource(navItem.iconTextId)) },
                        onClick = { navigator.navigateTo(navKey) }
                    )
                }
            }
        ) {
            Scaffold(
                modifier = modifier,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
            ) { padding ->
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .consumeWindowInsets(padding)
                        .windowInsetsPadding(
                            WindowInsets.safeDrawing.only(
                                WindowInsetsSides.Horizontal,
                            ),
                        ),
                ) {
                    Box(
                        modifier = Modifier.consumeWindowInsets(
                            WindowInsets(0, 0, 0, 0)
                        ),
                    ) {
                        CbNavHost(
                            navigator = navigator,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}


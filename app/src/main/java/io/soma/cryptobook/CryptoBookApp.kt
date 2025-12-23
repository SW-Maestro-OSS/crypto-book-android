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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import io.soma.cryptobook.coindetail.presentation.navigation.coinDetailEntry
import io.soma.cryptobook.coinlist.presentation.navigation.coinListEntry
import io.soma.cryptobook.navigation.CbNavigator
import io.soma.cryptobook.navigation.LinkRouter
import io.soma.cryptobook.navigation.NavCommand
import io.soma.cryptobook.navigation.NavCommandSource
import io.soma.cryptobook.navigation.TOP_LEVEL_NAV_ITEMS
import io.soma.cryptobook.settings.presentation.navigation.settingsEntry

@Composable
fun CryptoBookApp(
    navSource: NavCommandSource,
    linkRouter: LinkRouter,
    initialScreen: NavKey,
    modifier: Modifier = Modifier,
) {
    val backStack = rememberNavBackStack(initialScreen)
    val navigator = remember(backStack) { CbNavigator(backStack) }

    LaunchedEffect(Unit) {
        navSource.commands.collect { cmd ->
            when (cmd) {
                is NavCommand.Navigate -> {
                    val key = linkRouter.resolve(cmd.page)
                    navigator.navigateTo(key)
                }

                is NavCommand.DeepLink -> {
                    val key = linkRouter.resolve(cmd.link)
                    navigator.navigateAsRoot(key)
                }

                is NavCommand.Back -> {
                    navigator.goBack()
                }
            }
        }
    }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TOP_LEVEL_NAV_ITEMS.forEach { (navKey, navItem) ->
                val selected =
                    navKey == navigator.backStack.lastOrNull { it in TOP_LEVEL_NAV_ITEMS.keys }
                item(
                    selected = selected,
                    icon = { Icon(navItem.icon, contentDescription = null) },
                    label = { Text(stringResource(navItem.iconTextId)) },
                    onClick = { navigator.navigateAsRoot(navKey) },
                )
            }
        },
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
                        WindowInsets(0, 0, 0, 0),
                    ),
                ) {
                    NavDisplay(
                        backStack = backStack,
                        onBack = { navigator.goBack() },
                        modifier = modifier,
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator(),
                        ),
                        entryProvider = entryProvider {
                            settingsEntry()
                            coinListEntry()
                            coinDetailEntry()
                        },
                    )
                }
            }
        }
    }
}
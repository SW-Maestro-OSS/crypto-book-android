package io.soma.cryptobook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode.Companion.Screen
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import io.soma.cryptobook.feature.coin_list.CoinListRoute

@Composable
fun CbNavHost(
    navigator: CbNavigator,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = navigator.backStack,
        onBack = { navigator.goBack() },
        modifier = modifier,
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<CbScreen.CoinList> {
                CoinListRoute(
                    onNavigateToCoinDetail = navigator::navigateToCoinDetail,
                )
            }
            entry<CbScreen.CoinDetail> { screen ->
//                CoinDetailRoute(
//                    symbol = screen.symbol,
//                    navigator = navigator
//                )
            }
            entry<CbScreen.Settings> {
//                SettingsRoute(navigator = navigator)
            }
        }
    )
}
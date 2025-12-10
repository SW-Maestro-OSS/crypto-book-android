package io.soma.cryptobook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

@Stable
class CbNavigator(val backStack: NavBackStack<NavKey>) {
    fun navigateTo(screen: CbScreen) {
        backStack.add(screen)
    }

    fun navigateToCoinDetail(symbol: String) {
        backStack.add(CbScreen.CoinDetail(symbol))
    }

    fun navigateToSettings() {
        backStack.add(CbScreen.Settings)
    }

    fun goBack(): Boolean {
        return if (backStack.size > 1) {
            backStack.removeLastOrNull()
            true
        } else {
            false
        }
    }
}

@Composable
fun rememberCbNavigator (
    startDestination: CbScreen = CbScreen.CoinList
): CbNavigator {
    val backStack = rememberNavBackStack(startDestination)
    return remember(backStack) { CbNavigator(backStack) }
}

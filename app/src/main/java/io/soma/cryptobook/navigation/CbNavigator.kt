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
        backStack.remove(screen)
        backStack.add(screen)
    }

    fun navigateAsRoot(screen: CbScreen) {
        backStack.clear()
        backStack.add(screen)
    }

    fun navigateToCoinDetail(symbol: String) {
        backStack.add(CbScreen.CoinDetail(symbol))
    }

    // 이 로직 수정되어야함
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
fun rememberCbNavigator(startDestination: CbScreen = CbScreen.CoinList): CbNavigator {
    val backStack = rememberNavBackStack(startDestination)
    return remember(backStack) { CbNavigator(backStack) }
}

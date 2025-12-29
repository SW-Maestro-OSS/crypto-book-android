package io.soma.cryptobook.main.presentation.navigation

import androidx.compose.runtime.Stable
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey

@Stable
class CbNavigator(val backStack: NavBackStack<NavKey>) {
    fun navigateTo(key: NavKey) {
        backStack.remove(key)
        backStack.add(key)
    }

    fun navigateAsRoot(key: NavKey) {
        backStack.clear()
        backStack.add(key)
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

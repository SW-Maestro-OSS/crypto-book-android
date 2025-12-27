package io.soma.cryptobook.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

@Composable
fun rememberNavigationState(startKey: NavKey, topLevelKeys: Set<NavKey>): NavigationState {
    val backStack = rememberNavBackStack(startKey)

    // 사실 key 지정 필요없음
    return remember(startKey, topLevelKeys) {
        NavigationState(
            startKey = startKey,
            backStack = backStack,
            topLevelKeys = topLevelKeys,
        )
    }
}

class NavigationState(
    val startKey: NavKey,
    val backStack: NavBackStack<NavKey>,
    val topLevelKeys: Set<NavKey>,
) {
    val currentTopKey: NavKey by derivedStateOf { backStack.last() }
}

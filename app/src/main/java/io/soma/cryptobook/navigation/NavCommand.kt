package io.soma.cryptobook.navigation

import kotlinx.coroutines.flow.SharedFlow

sealed interface NavCommand {
    data class Navigate(val link: String) : NavCommand
    data class DeepLink(val link: String) : NavCommand
    data object Back : NavCommand
}

interface NavCommandSource {
    val commands: SharedFlow<NavCommand>
}

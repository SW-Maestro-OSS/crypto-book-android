package io.soma.cryptobook.navigation

import io.soma.cryptobook.core.domain.navigation.AppPage
import kotlinx.coroutines.flow.SharedFlow

sealed interface NavCommand {
    data class Navigate(val page: AppPage) : NavCommand
    data class DeepLink(val link: String) : NavCommand
    data object Back : NavCommand
}

interface NavCommandSource {
    val commands: SharedFlow<NavCommand>
}

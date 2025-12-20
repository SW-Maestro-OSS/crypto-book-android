package io.soma.cryptobook.core.domain.navigation

interface NavigationHelper {
    fun navigate(link: String)
    fun deepLink(link: String)
    fun back()
}

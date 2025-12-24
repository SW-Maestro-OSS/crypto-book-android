package io.soma.cryptobook.core.domain.navigation

interface NavigationHelper {
    fun navigate(link: AppPage)
    fun deepLink(link: String)
    fun back()
}

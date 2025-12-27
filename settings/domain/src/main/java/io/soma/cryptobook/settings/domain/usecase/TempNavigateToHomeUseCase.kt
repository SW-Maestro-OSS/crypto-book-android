package io.soma.cryptobook.settings.domain.usecase

import io.soma.cryptobook.core.domain.navigation.AppPage
import io.soma.cryptobook.core.domain.navigation.NavigationHelper
import javax.inject.Inject

class TempNavigateToHomeUseCase @Inject constructor(
    private val navigationHelper: NavigationHelper,
) {
    suspend operator fun invoke() {
        navigationHelper.navigate(AppPage.Home)
    }
}

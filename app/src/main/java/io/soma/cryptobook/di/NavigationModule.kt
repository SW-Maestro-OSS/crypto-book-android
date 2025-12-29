package io.soma.cryptobook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.soma.cryptobook.core.domain.navigation.NavigationHelper
import io.soma.cryptobook.main.presentation.navigation.NavCommandSource
import io.soma.cryptobook.main.presentation.navigation.NavigationHelperImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class NavigationModule {
    @Binds
    abstract fun bindNavigationHelper(impl: NavigationHelperImpl): NavigationHelper

    @Binds
    abstract fun bindNavCommandSource(impl: NavigationHelperImpl): NavCommandSource
}

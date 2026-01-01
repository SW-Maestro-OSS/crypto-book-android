package io.soma.cryptobook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.soma.cryptobook.splash.data.repository.AppInitRepositoryImpl
import io.soma.cryptobook.splash.domain.repository.AppInitRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SplashDataModule {
    @Binds
    @Singleton
    abstract fun bindAppInitRepository(impl: AppInitRepositoryImpl): AppInitRepository
}

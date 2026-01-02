package io.soma.cryptobook.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.soma.cryptobook.core.data.repository.UserDataRepositoryImpl
import io.soma.cryptobook.core.domain.repository.UserDataRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsDataModule {
    @Binds
    @Singleton
    abstract fun bindCoinRepository(impl: UserDataRepositoryImpl): UserDataRepository
}

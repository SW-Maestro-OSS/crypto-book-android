package io.soma.cryptobook.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.soma.cryptobook.data.repository.CoinRepositoryImpl
import io.soma.cryptobook.domain.repository.CoinRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCoinRepository(
        impl: CoinRepositoryImpl
    ): CoinRepository
}
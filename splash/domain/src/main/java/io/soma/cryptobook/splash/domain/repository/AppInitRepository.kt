package io.soma.cryptobook.splash.domain.repository

import io.soma.cryptobook.splash.domain.model.AppVersionInfoVO

interface AppInitRepository {
    suspend fun getAppVersionInfo(): AppVersionInfoVO
}

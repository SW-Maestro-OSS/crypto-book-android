package io.soma.cryptobook.splash.data.repository

import io.soma.cryptobook.splash.data.network.CryptoBookApiService
import io.soma.cryptobook.splash.domain.model.AppVersionInfoVO
import io.soma.cryptobook.splash.domain.repository.AppInitRepository
import javax.inject.Inject

class AppInitRepositoryImpl @Inject constructor(
    private val cryptoBookApiService: CryptoBookApiService,
) : AppInitRepository {
    override suspend fun getAppVersionInfo(): AppVersionInfoVO {
// TODO: 백엔드 연결 후 아래 fake 데이터 제거하고 API 호출로 교체
//        val response = cryptoBookApiService.getAppVersion()
        val response = AppVersionInfoVO(
            minVersion = "1.0.0",
            latestVersion = "1.3.0",
        )
        return AppVersionInfoVO(
            minVersion = response.minVersion,
            latestVersion = response.latestVersion,
        )
    }
}

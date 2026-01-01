package io.soma.cryptobook.splash.data.network

import io.soma.cryptobook.splash.data.model.AppVersionDTO
import retrofit2.http.GET

interface CryptoBookApiService {
    @GET("app/version")
    suspend fun getAppVersion(): AppVersionDTO
}

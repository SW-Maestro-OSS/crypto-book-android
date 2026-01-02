package io.soma.cryptobook.core.data.network

import io.soma.cryptobook.core.data.model.ExchangeRateDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangeApiService {
    @GET("site/program/financial/exchangeJSON")
    suspend fun getExchangeRates(
        @Query("authkey") authKey: String,
        @Query("searchdate") searchDate: String,
        @Query("data") data: String = "AP01",
    ): Response<List<ExchangeRateDto>>
}

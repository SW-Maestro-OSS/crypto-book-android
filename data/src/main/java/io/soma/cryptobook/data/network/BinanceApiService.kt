package io.soma.cryptobook.data.network

import io.soma.cryptobook.data.model.BinanceTickerDto
import retrofit2.http.GET

interface BinanceApiService {
    @GET("api/v3/ticker/price")
    suspend fun getAllTikcerPrices(): List<BinanceTickerDto>
}
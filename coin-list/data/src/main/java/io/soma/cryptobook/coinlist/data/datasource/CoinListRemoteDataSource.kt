package io.soma.cryptobook.coinlist.data.datasource

import io.soma.cryptobook.coinlist.data.model.BinanceTickerDto
import io.soma.cryptobook.coinlist.data.network.BinanceApiService
import io.soma.cryptobook.core.network.base.BaseDataSource
import javax.inject.Inject

class CoinListRemoteDataSource @Inject constructor(
    private val apiService: BinanceApiService
) : BaseDataSource() {
    suspend fun getAllTickerPrices(): List<BinanceTickerDto> = checkResponse(apiService.getAllTickerPrices())
}
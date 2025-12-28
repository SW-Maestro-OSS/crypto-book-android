package io.soma.cryptobook.core.domain.repository

import io.soma.cryptobook.core.domain.model.CoinInfoVO
import io.soma.cryptobook.core.domain.model.CoinPriceVO
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    suspend fun getCoinPrices(): List<CoinPriceVO>
    suspend fun getCoinInfoList(): List<CoinInfoVO>

    // 웹소켓용은 추후 구현
    fun observeCoinPrices(): Flow<List<CoinPriceVO>>
}

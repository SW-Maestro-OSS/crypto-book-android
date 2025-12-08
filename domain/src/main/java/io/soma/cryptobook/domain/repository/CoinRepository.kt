package io.soma.cryptobook.domain.repository

import io.soma.cryptobook.domain.model.CoinInfoVO
import io.soma.cryptobook.domain.model.CoinPriceVO

interface CoinRepository {
    suspend fun getCoinPrices(): List<CoinPriceVO>
    suspend fun getCoinInfoList(): List<CoinInfoVO>
    // 웹소켓용은 추후 구현
}
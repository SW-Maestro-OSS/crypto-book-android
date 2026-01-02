package io.soma.cryptobook.coindetail.domain.repository

import io.soma.cryptobook.core.domain.model.CoinPriceVO
import kotlinx.coroutines.flow.Flow

interface CoinDetailRepository {
    fun observeCoinDetail(symbol: String): Flow<CoinPriceVO>
}
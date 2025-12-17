package io.soma.cryptobook.coinlist.data.repository

import io.soma.cryptobook.coinlist.data.datasource.CoinListRemoteDataSource
import io.soma.cryptobook.coinlist.data.model.toCoinPriceVO
import io.soma.cryptobook.coinlist.domain.model.CoinInfoVO
import io.soma.cryptobook.coinlist.domain.model.CoinPriceVO
import io.soma.cryptobook.coinlist.domain.repository.CoinRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinListRemoteDataSource: CoinListRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CoinRepository {
    override suspend fun getCoinPrices(): List<CoinPriceVO> {
        return withContext(ioDispatcher) {
            coinListRemoteDataSource.getAllTickerPrices().map { it.toCoinPriceVO()}
        }
    }


    override suspend fun getCoinInfoList(): List<CoinInfoVO> {
        TODO("Not yet implemented")
    }
}
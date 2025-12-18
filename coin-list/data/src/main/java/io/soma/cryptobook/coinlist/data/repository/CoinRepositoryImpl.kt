package io.soma.cryptobook.coinlist.data.repository

import io.soma.cryptobook.coinlist.data.datasource.CoinListRemoteDataSource
import io.soma.cryptobook.coinlist.data.datasource.CoinListStreamDataSource
import io.soma.cryptobook.coinlist.data.model.toCoinPriceVO
import io.soma.cryptobook.coinlist.domain.error.WebSocketDisconnectedException
import io.soma.cryptobook.coinlist.domain.model.CoinInfoVO
import io.soma.cryptobook.coinlist.domain.model.CoinPriceVO
import io.soma.cryptobook.coinlist.domain.repository.CoinRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val coinListRemoteDataSource: CoinListRemoteDataSource,
    private val coinListStreamDataSource: CoinListStreamDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : CoinRepository {
    override suspend fun getCoinPrices(): List<CoinPriceVO> {
        return withContext(ioDispatcher) {
            coinListRemoteDataSource.getAllTickerPrices().map { it.toCoinPriceVO()}
        }
    }

    override fun observeCoinPrices(): Flow<List<CoinPriceVO>> = flow {
        coinListStreamDataSource.connect()
        try {
            coinListStreamDataSource.observeCoinList()
                .collect { state ->
                    when (state) {
                        is CoinListStreamDataSource.State.Success -> {
                            emit(state.tickers.map { it.toCoinPriceVO() })
                        }
                        is CoinListStreamDataSource.State.Error -> {
                            throw state.throwable
                        }
                        is CoinListStreamDataSource.State.Disconnected -> {
                            throw WebSocketDisconnectedException()
                        }
                        is CoinListStreamDataSource.State.Connected -> {}
                    }
                }
        } finally {
            coinListStreamDataSource.disconnect()
        }
    }.flowOn(ioDispatcher)

    override suspend fun getCoinInfoList(): List<CoinInfoVO> {
        TODO("Not yet implemented")
    }
}
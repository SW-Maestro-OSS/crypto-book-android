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

class CoinRepositoryImpl
@Inject
constructor(
    private val coinListRemoteDataSource: CoinListRemoteDataSource,
    private val coinListStreamDataSource: CoinListStreamDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : CoinRepository {

    private val cache = LinkedHashMap<String, CoinPriceVO>()

    override suspend fun getCoinPrices(): List<CoinPriceVO> = withContext(ioDispatcher) {
            coinListRemoteDataSource.getAllTickerPrices().map { it.toCoinPriceVO() }
    }

    override fun observeCoinPrices(): Flow<List<CoinPriceVO>> = flow {
        if (cache.isEmpty()) {
            try {
                val initialData = getCoinPrices()
                initialData.forEach { cache[it.symbol] = it }
            } catch (e: Exception) {}
        }

        if (cache.isNotEmpty()) {
            emit(cache.values.toList())
        }

        coinListStreamDataSource.observeCoinList().collect { state ->
            when (state) {
                is CoinListStreamDataSource.State.Success -> {
                    state.tickers.forEach { ticker ->
                        cache[ticker.symbol] = ticker.toCoinPriceVO()
                    }
                    emit(cache.values.toList())
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
    }.flowOn(ioDispatcher)

    override suspend fun getCoinInfoList(): List<CoinInfoVO> {
        TODO("Not yet implemented")
    }
}

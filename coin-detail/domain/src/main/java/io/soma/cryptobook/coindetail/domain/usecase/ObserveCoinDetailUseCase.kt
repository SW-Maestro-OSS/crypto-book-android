package io.soma.cryptobook.coindetail.domain.usecase

import io.soma.cryptobook.coindetail.domain.repository.CoinDetailRepository
import io.soma.cryptobook.core.domain.error.WebSocketDisconnectedException
import io.soma.cryptobook.core.domain.model.CoinPriceVO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
 import javax.inject.Inject

class ObserveCoinDetailUseCase @Inject constructor(
    private val repository: CoinDetailRepository
) {
    sealed class Result {
        data class Success(val coinDetail: CoinPriceVO) : Result()
        sealed class Error : Result() {
            data class Connection(val throwable: Throwable) : Error()
            object Disconnected : Error()
        }
    }

    operator fun invoke(symbol: String): Flow<Result> = repository.observeCoinDetail(symbol)
            .map<CoinPriceVO, Result> { coinDetail ->
                Result.Success(coinDetail)
            }.catch { e ->
                when (e) {
                    is WebSocketDisconnectedException -> {
                        emit(Result.Error.Disconnected)
                    }

                    else -> {
                        emit(Result.Error.Connection(e))
                    }
                }
            }


}
package io.soma.cryptobook.coinlist.domain.usecase

import io.soma.cryptobook.coinlist.domain.error.HttpResponseException
import io.soma.cryptobook.coinlist.domain.error.HttpResponseStatus
import io.soma.cryptobook.coinlist.domain.model.CoinPriceVO
import io.soma.cryptobook.coinlist.domain.repository.CoinRepository
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GetCoinListUseCase @Inject constructor(
    private val coinRepository: CoinRepository,
) {
    sealed class Result {
        data class Success(val coinList: List<CoinPriceVO>) : Result()
        sealed class Error : Result() {
            object Network : Error()
            object Server : Error()
            data class Unknown(val message: String?) : Error()
        }
    }
    suspend operator fun invoke(): Result {
        return try {
            val coins = coinRepository.getCoinPrices()
            Result.Success(coins)
        } catch (e: CancellationException) { // coroutines 취소 전파
            throw e
        } catch (e: HttpResponseException) {
            when (e.status) {
                HttpResponseStatus.InternalError -> Result.Error.Server
                else -> Result.Error.Unknown(e.message)
            }
        } catch (e: IOException) {
            Result.Error.Network
        } catch (e: Exception) {
            Result.Error.Unknown(e.message)
        }
    }
}

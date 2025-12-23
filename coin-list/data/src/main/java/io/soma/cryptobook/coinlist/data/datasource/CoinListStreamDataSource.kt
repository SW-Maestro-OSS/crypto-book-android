package io.soma.cryptobook.coinlist.data.datasource

import io.soma.cryptobook.coinlist.data.model.CoinTickerDto
import io.soma.cryptobook.core.network.BinanceWebSocketClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import javax.inject.Inject

class CoinListStreamDataSource @Inject constructor(
    private val webSocketClient: BinanceWebSocketClient,
    private val json: Json,
) {
    sealed class State {
        data class Success(val tickers: List<CoinTickerDto>) : State()
        data class Error(val throwable: Throwable) : State()
        data object Connected : State()
        data object Disconnected : State()
    }

    fun connect() {
        webSocketClient.connect("ws/!ticker@arr")
    }

    fun disconnect() {
        webSocketClient.disconnect()
    }

    fun observeCoinList(): Flow<State> {
        return webSocketClient.observeEvents()
            .mapNotNull { event ->
                when (event) {
                    is BinanceWebSocketClient.Event.Connected -> {
                        State.Connected
                    }
                    is BinanceWebSocketClient.Event.Message -> {
                        try {
                            val tickers = json.decodeFromString<List<CoinTickerDto>>(event.message)
                            State.Success(tickers)
                        } catch (e: SerializationException) {
                            State.Error(e)
                        }
                    }
                    is BinanceWebSocketClient.Event.Error -> {
                        State.Error(event.throwable)
                    }
                    is BinanceWebSocketClient.Event.Disconnected -> {
                        State.Disconnected
                    }
                }
            }
    }
}

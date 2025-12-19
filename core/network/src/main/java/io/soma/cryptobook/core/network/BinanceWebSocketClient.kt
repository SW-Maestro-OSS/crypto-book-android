package io.soma.cryptobook.core.network

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject

class BinanceWebSocketClient @Inject constructor(
    private val client: OkHttpClient
) {
    sealed class Event {
        data class Message(val message: String) : Event()
        data class Error(val throwable: Throwable) : Event()
        data object Connected : Event()
        data object Disconnected : Event()
    }

//    private var retryCount = 0

    private val _events = MutableSharedFlow<Event>(extraBufferCapacity = 64)

    companion object {
        private const val BASE_URL = "wss://fstream.binance.com/ws/"
    }

    private var webSocket: WebSocket? = null

    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            _events.tryEmit(Event.Connected)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            _events.tryEmit(Event.Message(text))
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            _events.tryEmit(Event.Error(t))
            this@BinanceWebSocketClient.webSocket = null
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            _events.tryEmit(Event.Disconnected)
            this@BinanceWebSocketClient.webSocket = null
        }
    }


    fun connect(path: String) {
        if (webSocket != null) return

        val request = Request.Builder()
            .url(BASE_URL + path)
            .build()

        webSocket = client.newWebSocket(request, listener)
    }

    fun disconnect() {
        webSocket?.close(1000, "Normal closure")
        webSocket = null
    }

    fun observeEvents(): Flow<Event> = _events.asSharedFlow()

//    private fun reconnectWithBackoff() {
//        retryCount++
//        val baseDelay = 1000L * 2.0.pow(retryCount.coerceAtMost(5)).toLong()
//        val jitter = (0..1000).random()
//        val delayMs = (baseDelay + jitter).coerceAtMost(6000L)
//        CoroutineScope(Dispatchers.IO).launch {
//            delay(delayMs)
//            connect()
//        }
//    }
}
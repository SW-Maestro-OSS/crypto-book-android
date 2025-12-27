package io.soma.cryptobook.core.network

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONArray
import org.json.JSONObject
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

class BinanceWebSocketClient @Inject constructor(
    private val client: OkHttpClient,
) {
    sealed class Event {
        data class Message(val message: String) : Event()
        data class Error(val throwable: Throwable) : Event()
        data object Connected : Event()
        data object Disconnected : Event()
    }

    private val requestIdCounter = AtomicInteger(1)

    private val _isConnected = AtomicBoolean(false)
    val isConnected: Boolean get() = _isConnected.get()
//    private var retryCount = 0

    private val _events = MutableSharedFlow<Event>(
        extraBufferCapacity = 64,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val events = _events.asSharedFlow()

    companion object {
        private const val BASE_URL = "wss://fstream.binance.com/ws"
    }

    private var webSocket: WebSocket? = null

    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            _isConnected.set(true)
            _events.tryEmit(Event.Connected)
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            _events.tryEmit(Event.Message(text))
        }

        // TODO: 재시도 로직 추가(exponential backoff)
        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            _isConnected.set(false)
            _events.tryEmit(Event.Error(t))
            this@BinanceWebSocketClient.webSocket = null
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            _isConnected.set(false)
            _events.tryEmit(Event.Disconnected)
            this@BinanceWebSocketClient.webSocket = null
        }
    }

    fun connect() {
        if (webSocket != null) return
        val request = Request.Builder().url(BASE_URL).build()
        webSocket = client.newWebSocket(request, listener)
    }

    fun disconnect() {
        _isConnected.set(false)
        webSocket?.close(1000, "Normal closure")
        webSocket = null
    }

    fun subscribe(streams: List<String>) {
        if (!isConnected) return
        val id = requestIdCounter.getAndIncrement()
        val json = createSubscriptionJson("SUBSCRIBE", streams, id)
        webSocket?.send(json)
    }

    fun unsubscribe(streams: List<String>) {
        if (!isConnected) return
        val id = requestIdCounter.getAndIncrement()
        val json = createSubscriptionJson("UNSUBSCRIBE", streams, id)
        webSocket?.send(json)
    }

    private fun createSubscriptionJson(method: String, params: List<String>, id: Int): String =
        JSONObject().apply {
            put("method", method)
            put("params", JSONArray(params))
            put("id", id)
        }.toString()

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

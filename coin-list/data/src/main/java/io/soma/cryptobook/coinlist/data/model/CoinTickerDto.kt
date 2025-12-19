package io.soma.cryptobook.coinlist.data.model

import io.soma.cryptobook.coinlist.domain.model.CoinPriceVO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinTickerDto(
    @SerialName("s") val symbol: String,            // 심볼 (BTCUSDT)
    @SerialName("c") val lastPrice: String,         // 현재가
    @SerialName("P") val priceChangePercent: String // 24시간 변동률

)

fun CoinTickerDto.toCoinPriceVO() = CoinPriceVO(
    symbol = symbol,
    price = lastPrice.toBigDecimal(),
    priceChangePercentage24h = priceChangePercent.toDouble()
)

package io.soma.cryptobook.home.data.model

import io.soma.cryptobook.home.domain.model.CoinPriceVO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinanceTickerDto(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("price")
    val price: String,
    @SerialName("priceChangePercent")
    val priceChangePercent: String,
)

fun BinanceTickerDto.toCoinPriceVO() = CoinPriceVO(
    symbol = symbol,
    price = price.toBigDecimal(),
    priceChangePercentage24h = priceChangePercent.toDouble(),
)

package io.soma.cryptobook.coinlist.data.model

import io.soma.cryptobook.coinlist.domain.model.CoinPriceVO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BinanceTickerDto(
    @SerialName("symbol")
    val symbol: String,
    @SerialName("price")
    val price: String
)

fun BinanceTickerDto.toCoinPriceVO() = CoinPriceVO(
    symbol = symbol,
    price = price.toBigDecimal(),
    priceChangePercentage24h = 0.0
)
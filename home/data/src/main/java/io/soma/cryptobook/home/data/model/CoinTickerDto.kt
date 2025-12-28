package io.soma.cryptobook.home.data.model

import io.soma.cryptobook.core.domain.model.CoinPriceVO
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CoinTickerDto(
    /** 심볼 (BTCUSDT) */
    @SerialName("s") val symbol: String,

    /** 현재가 */
    @SerialName("c") val lastPrice: String,

    /** 24시간 변동률 */
    @SerialName("P") val priceChangePercent: String,
)


fun CoinTickerDto.toCoinPriceVO() = CoinPriceVO(
    symbol = symbol,
    price = lastPrice.toBigDecimal(),
    priceChangePercentage24h = priceChangePercent.toDouble(),
)

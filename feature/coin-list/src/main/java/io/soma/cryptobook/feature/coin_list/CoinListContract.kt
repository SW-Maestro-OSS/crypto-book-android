package io.soma.cryptobook.feature.coin_list

import io.soma.cryptobook.core.ui.Event
import io.soma.cryptobook.core.ui.SideEffect
import io.soma.cryptobook.core.ui.UiState
import io.soma.cryptobook.domain.model.CoinPriceVO
import java.math.BigDecimal

data class CoinListUiState(
    val coins: List<CoinItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
) : UiState

data class CoinItem(
    val symbol: String,
    val price: BigDecimal,
    val priceChangePercentage24h: Double,
)

sealed class CoinListEvent : Event {
    object OnScreenLoad : CoinListEvent()
    object OnBackClicked : CoinListEvent()
    data class OnCoinClicked(val symbol: String) : CoinListEvent()
}

sealed class CoinListSideEffect : SideEffect {
    object Close : CoinListSideEffect()
    data class NavigateToCoinDetail(val symbol: String) : CoinListSideEffect()
    data class ShowToast(val message: String) : CoinListSideEffect()
}

fun CoinPriceVO.toCoinItem() = CoinItem(
    symbol = symbol,
    price = price,
    priceChangePercentage24h = priceChangePercentage24h,
)
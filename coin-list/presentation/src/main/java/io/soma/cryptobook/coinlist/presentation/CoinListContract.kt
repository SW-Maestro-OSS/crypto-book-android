package io.soma.cryptobook.coinlist.presentation

import io.soma.cryptobook.coinlist.domain.model.CoinPriceVO
import io.soma.cryptobook.core.presentation.Event
import io.soma.cryptobook.core.presentation.SideEffect
import io.soma.cryptobook.core.presentation.UiState
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
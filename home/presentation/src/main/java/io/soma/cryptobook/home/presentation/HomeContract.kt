package io.soma.cryptobook.home.presentation

import io.soma.cryptobook.core.domain.model.CoinPriceVO
import io.soma.cryptobook.core.presentation.Event
import io.soma.cryptobook.core.presentation.SideEffect
import io.soma.cryptobook.core.presentation.UiState
import java.math.BigDecimal

data class HomeUiState(
    val coins: List<CoinItem> = emptyList(),
    val isLoading: Boolean = false,
    val errorMsg: String? = null,
) : UiState

data class CoinItem(
    val symbol: String,
    val price: BigDecimal,
    val priceChangePercentage24h: Double,
)

sealed class HomeEvent : Event {
    object OnRefresh : HomeEvent()
    object OnBackClicked : HomeEvent()
    data class OnCoinClicked(val symbol: String) : HomeEvent()
}

sealed class HomeSideEffect : SideEffect {
    object Close : HomeSideEffect()
    data class NavigateToCoinDetail(val symbol: String) : HomeSideEffect()
    data class ShowToast(val message: String) : HomeSideEffect()
}

fun CoinPriceVO.toCoinItem() = CoinItem(
    symbol = symbol,
    price = price,
    priceChangePercentage24h = priceChangePercentage24h,
)

package io.soma.cryptobook.coindetail.presentation

import io.soma.cryptobook.core.presentation.Event
import io.soma.cryptobook.core.presentation.SideEffect
import io.soma.cryptobook.core.presentation.UiState
import java.math.BigDecimal

data class CoinDetailUiState(
    val symbol: String = "",
    val price: BigDecimal = BigDecimal.ZERO,
    val priceChangePercentage24h: Double = 0.0,
    val isLoading: Boolean = true,
    val errorMsg: String? = null,
) : UiState

sealed interface CoinDetailEvent : Event {
    data object OnBackClicked : CoinDetailEvent
}

sealed interface CoinDetailSideEffect : SideEffect

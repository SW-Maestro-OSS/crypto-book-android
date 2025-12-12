package io.soma.cryptobook.coinlist.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.soma.cryptobook.coinlist.domain.repository.CoinRepository
import io.soma.cryptobook.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val coinRepository: CoinRepository
) :
    BaseViewModel<CoinListEvent, CoinListUiState, CoinListSideEffect>(CoinListUiState()) {
    override fun handleEvent(event: CoinListEvent) {
        when (event) {
            CoinListEvent.OnScreenLoad -> loadCoins()
            CoinListEvent.OnBackClicked -> sendSideEffect { CoinListSideEffect.Close }
            is CoinListEvent.OnCoinClicked -> sendSideEffect {
                CoinListSideEffect.NavigateToCoinDetail(event.symbol)
            }
        }
    }

    private fun loadCoins() {
        // 코인 목록 로드 로직 구현
        viewModelScope.launch {
            setUiState { copy(isLoading = true) }

            try {
                val data = coinRepository.getCoinPrices().map { it.toCoinItem() }
                setUiState { copy(isLoading = false, coins = data) }
            } catch (e: Exception) {
                setUiState { copy(isLoading = false, errorMsg = e.message) }
                sendSideEffect { CoinListSideEffect.ShowToast(e.message ?: "Unknown Error") }
            }

        }
    }
}
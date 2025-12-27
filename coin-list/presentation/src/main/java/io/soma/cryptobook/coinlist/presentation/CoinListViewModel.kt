package io.soma.cryptobook.coinlist.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.soma.cryptobook.coinlist.domain.usecase.GetCoinListUseCase
import io.soma.cryptobook.coinlist.domain.usecase.ObserveCoinListUseCase
import io.soma.cryptobook.core.domain.navigation.NavigationHelper
import io.soma.cryptobook.core.presentation.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val observeCoinListUseCase: ObserveCoinListUseCase,
    val navigationHelper: NavigationHelper,
) :
    BaseViewModel<CoinListEvent, CoinListUiState, CoinListSideEffect>(CoinListUiState()) {
    override fun handleEvent(event: CoinListEvent) {
        when (event) {
            CoinListEvent.OnScreenLoad -> {
                observeCoins()
            }

            CoinListEvent.OnBackClicked -> sendSideEffect { CoinListSideEffect.Close }
            is CoinListEvent.OnCoinClicked -> sendSideEffect {
                CoinListSideEffect.NavigateToCoinDetail(event.symbol)
            }
        }
    }

    private fun observeCoins() {
        viewModelScope.launch {
            observeCoinListUseCase().collect { result ->
                when (result) {
                    is ObserveCoinListUseCase.Result.Success -> {
                        setUiState {
                            copy(
                                isLoading = false,
                                errorMsg = null,
                                coins = result.coinList.map { it.toCoinItem() },
                            )
                        }
                    }

                    is ObserveCoinListUseCase.Result.Error.Connection -> {
                        setUiState { copy(errorMsg = "연결 오류") }
                        sendSideEffect {
                            CoinListSideEffect.ShowToast("실시간 연결 오류")
                        }
                    }

                    is ObserveCoinListUseCase.Result.Error.Disconnected -> {
                        setUiState { copy(errorMsg = "연결 끊김") }
                        sendSideEffect {
                            CoinListSideEffect.ShowToast("실시간 연결이 끊겼습니다")
                        }
                    }
                }
            }
        }
    }

    private fun loadCoins() {
        // 코인 목록 로드 로직 구현
        viewModelScope.launch {
            setUiState { copy(isLoading = true) }

            when (val result = getCoinListUseCase()) {
                is GetCoinListUseCase.Result.Success -> {
                    setUiState {
                        copy(
                            isLoading = false,
                            coins = result.coinList.map { it.toCoinItem() },
                        )
                    }
                }

                is GetCoinListUseCase.Result.Error -> {
                    setUiState { copy(isLoading = false) }
                    handleLoadError(result)
                }
            }
        }
    }

    private fun handleLoadError(error: GetCoinListUseCase.Result.Error) {
        when (error) {
            is GetCoinListUseCase.Result.Error.Network -> {
                setUiState { copy(errorMsg = "인터넷 연결을 확인해주세요") }
                sendSideEffect { CoinListSideEffect.ShowToast("인터넷 연결을 확인해주세요") }
            }

            is GetCoinListUseCase.Result.Error.Server,
            is GetCoinListUseCase.Result.Error.Unknown,
                -> {
                sendSideEffect { CoinListSideEffect.ShowToast("잠시 후 다시 시도해주세요") }
            }
        }
    }
}

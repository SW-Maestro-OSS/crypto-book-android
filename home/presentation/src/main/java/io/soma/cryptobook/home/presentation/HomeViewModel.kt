package io.soma.cryptobook.home.presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.soma.cryptobook.core.domain.message.MessageHelper
import io.soma.cryptobook.core.domain.navigation.NavigationHelper
import io.soma.cryptobook.core.presentation.BaseViewModel
import io.soma.cryptobook.home.domain.usecase.GetCoinListUseCase
import io.soma.cryptobook.home.domain.usecase.ObserveCoinListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCoinListUseCase: GetCoinListUseCase,
    private val observeCoinListUseCase: ObserveCoinListUseCase,
    val navigationHelper: NavigationHelper,
    val messageHelper: MessageHelper,
) : BaseViewModel<HomeEvent, HomeUiState, HomeSideEffect>(HomeUiState()) {

    init {
        observeCoins()
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OnRefresh -> {
                observeCoins()
            }

            HomeEvent.OnBackClicked -> sendSideEffect { HomeSideEffect.Close }
            is HomeEvent.OnCoinClicked -> sendSideEffect {
                HomeSideEffect.NavigateToCoinDetail(event.symbol)
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
                            HomeSideEffect.ShowToast("실시간 연결 오류")
                        }
                    }

                    is ObserveCoinListUseCase.Result.Error.Disconnected -> {
                        setUiState { copy(errorMsg = "연결 끊김") }
                        sendSideEffect {
                            HomeSideEffect.ShowToast("실시간 연결이 끊겼습니다")
                        }
                    }
                }
            }
        }
    }

    private fun handleLoadError(error: GetCoinListUseCase.Result.Error) {
        when (error) {
            is GetCoinListUseCase.Result.Error.Network -> {
                setUiState { copy(errorMsg = "인터넷 연결을 확인해주세요") }
                sendSideEffect { HomeSideEffect.ShowToast("인터넷 연결을 확인해주세요") }
            }

            is GetCoinListUseCase.Result.Error.Server,
            is GetCoinListUseCase.Result.Error.Unknown,
            -> {
                sendSideEffect { HomeSideEffect.ShowToast("잠시 후 다시 시도해주세요") }
            }
        }
    }
}

package io.soma.cryptobook.coindetail.presentation

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.soma.cryptobook.coindetail.domain.usecase.ObserveCoinDetailUseCase
import io.soma.cryptobook.core.domain.message.MessageHelper
import io.soma.cryptobook.core.domain.navigation.NavigationHelper
import io.soma.cryptobook.core.presentation.BaseViewModel
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CoinDetailViewModel.Factory::class)
class CoinDetailViewModel @AssistedInject constructor(
    @Assisted private val coinName: String,
    private val observeCoinDetailUseCase: ObserveCoinDetailUseCase,
    private val navigationHelper: NavigationHelper,
    private val messageHelper: MessageHelper,
) : BaseViewModel<CoinDetailEvent, CoinDetailUiState, CoinDetailSideEffect>(
    CoinDetailUiState(symbol = coinName),
) {
    @AssistedFactory
    interface Factory {
        fun create(coinName: String): CoinDetailViewModel
    }

    init {
        observeCoinDetail()
    }

    override fun handleEvent(event: CoinDetailEvent) {
        when (event) {
            CoinDetailEvent.OnBackClicked -> {
                navigationHelper.back()
//                sendSideEffect { CoinDetailSideEffect.NavigateBack }
            }
        }
    }

    private fun observeCoinDetail() {
        viewModelScope.launch {
            observeCoinDetailUseCase(symbol = coinName).collect { result ->
                when (result) {
                    is ObserveCoinDetailUseCase.Result.Success -> {
                        setUiState {
                            copy(
                                isLoading = false,
                                errorMsg = null,
                                price = result.coinDetail.price,
                                priceChangePercentage24h =
                                result.coinDetail.priceChangePercentage24h,
                            )
                        }
                    }

                    is ObserveCoinDetailUseCase.Result.Error.Connection -> {
                        setUiState { copy(isLoading = false, errorMsg = "연결 오류") }
                        messageHelper.showToast("연결 오류가 발생했습니다")
//                        sendSideEffect { CoinDetailSideEffect.ShowToast("연결 오류가 발생했습니다") }
                    }

                    is ObserveCoinDetailUseCase.Result.Error.Disconnected -> {
                        setUiState { copy(isLoading = false, errorMsg = "연결 끊김") }
                        messageHelper.showToast("실시간 연결이 끊겼습니다")
//                        sendSideEffect { CoinDetailSideEffect.ShowToast("연결이 끊겼습니다") }
                    }
                }
            }
        }
    }
}

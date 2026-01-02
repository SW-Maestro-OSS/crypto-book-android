package io.soma.cryptobook.coindetail.presentation

import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel

@HiltViewModel(assistedFactory = CoinDetailViewModel.Factory::class)
class CoinDetailViewModel @AssistedInject constructor(
    @Assisted private val coinName: String,
    // TODO(hs) : 아래 이것 BaseViewModel로 변경해야 한다.
) : ViewModel() {
    @AssistedFactory
    interface Factory {
        fun create(coinName: String): CoinDetailViewModel
    }

    val tempCoinName = coinName
}

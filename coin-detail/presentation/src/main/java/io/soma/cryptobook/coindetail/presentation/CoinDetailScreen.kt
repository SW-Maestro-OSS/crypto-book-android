package io.soma.cryptobook.coindetail.presentation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun CoinDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: CoinDetailViewModel = hiltViewModel(),
) {
    val coinName = viewModel.tempCoinName

    CoinDetailScreen(
        modifier = modifier,
        coinName = coinName,
    )
}

@Composable
internal fun CoinDetailScreen(modifier: Modifier = Modifier, coinName: String) {
    Text(coinName)
}

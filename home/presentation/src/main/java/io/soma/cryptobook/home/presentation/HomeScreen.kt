package io.soma.cryptobook.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.math.RoundingMode

@Composable
fun HomeRoute(modifier: Modifier = Modifier, viewModel: HomeViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    HomeScreen(
        state = uiState,
        onEvent = viewModel::handleEvent,
        modifier = modifier,
    )
}

@Composable
internal fun HomeScreen(state: HomeUiState, onEvent: (HomeEvent) -> Unit, modifier: Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        state.errorMsg?.let { msg ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Red.copy(alpha = 0.1f))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = msg, color = Color.Red)
            }
        }
        Box(modifier = modifier.fillMaxSize()) {
            if (state.coins.isNotEmpty()) {
                LazyColumn {
                    items(state.coins, key = { it.symbol }) { coin ->
                        CoinItem(
                            coin = coin,
                            onClick = { onEvent(HomeEvent.OnCoinClicked(coin.symbol)) },
                        )
                    }
                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun CoinItem(coin: CoinItem, onClick: () -> Unit) {
    Row(
        modifier =
        Modifier
            .fillMaxWidth()
            .clickable { onClick() } // 클릭 리스너
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = coin.symbol)
        Text(text = "$${coin.price.setScale(2, RoundingMode.HALF_UP)}")
        Text(
            text = "${
                if (coin.priceChangePercentage24h >= 0) {
                    "+"
                } else {
                    ""
                }
            }${coin.priceChangePercentage24h}%",
            color = if (coin.priceChangePercentage24h >= 0) Color.Green else Color.Red,
        )
    }
}

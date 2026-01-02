package io.soma.cryptobook.coindetail.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun CoinDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: CoinDetailViewModel,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    CoinDetailScreen(
        state = state,
        onEvent = viewModel::handleEvent,
        modifier = modifier,

        )
}

@Composable
internal fun CoinDetailScreen(
    state: CoinDetailUiState,
    onEvent: (CoinDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator()
            }

            state.errorMsg != null -> {
                Text(
                    text = state.errorMsg,
                    color = MaterialTheme.colorScheme.error,
                )
            }

            else -> {
                CoinDetailContent(state = state)
            }
        }
    }
}

@Composable
private fun CoinDetailContent(
    state: CoinDetailUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = state.symbol,
            style = MaterialTheme.typography.headlineLarge,
        )

        Text(
            text = "$${state.price}",
            style = MaterialTheme.typography.displayMedium,
        )

        val changeColor = if (state.priceChangePercentage24h >= 0) Color.Green else Color.Red
        val changePrefix = if (state.priceChangePercentage24h >= 0) "+" else ""

        Text(
            text = "$changePrefix${String.format("%.2f", state.priceChangePercentage24h)}%",
            style = MaterialTheme.typography.titleLarge,
            color = changeColor,
        )
    }
}

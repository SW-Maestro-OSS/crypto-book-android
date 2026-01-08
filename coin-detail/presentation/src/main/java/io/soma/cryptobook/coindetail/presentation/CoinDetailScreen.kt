package io.soma.cryptobook.coindetail.presentation

import androidx.compose.foundation.background
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.math.BigDecimal
import io.soma.cryptobook.coindetail.presentation.component.PriceChange
import io.soma.cryptobook.coindetail.presentation.component.PriceChangeType
import io.soma.cryptobook.core.designsystem.theme.ScreenBackground
import io.soma.cryptobook.core.designsystem.theme.component.CbDetailTopAppBar
import java.math.RoundingMode

@Composable
fun CoinDetailRoute(modifier: Modifier = Modifier, viewModel: CoinDetailViewModel) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ScreenBackground)
    ) {
        CbDetailTopAppBar(
            onSearchClick = { },
            title = uiState.symbol,
            onBackClick = { },
            onFavoriteClick = { },
            modifier = modifier
        )
        CoinDetailScreen(
            state = uiState,
            onEvent = viewModel::handleEvent,
            modifier = modifier,
        )
    }
}

@Composable
internal fun CoinDetailScreen(
    state: CoinDetailUiState,
    onEvent: (CoinDetailEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
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
private fun CoinDetailContent(state: CoinDetailUiState, modifier: Modifier = Modifier) {
    val priceChangeType = when {
        state.priceChangePercentage24h > 0 -> PriceChangeType.Up
        state.priceChangePercentage24h < 0 -> PriceChangeType.Down
        else -> PriceChangeType.Flat
    }

    val changePrefix = if (state.priceChangePercentage24h >= 0) "+" else ""
    // TODO: priceChange 금액을 API에서 받아오기
    val priceChangeText = "{priceChange} ($changePrefix${String.format("%.2f", state.priceChangePercentage24h)}%)"

    Column(
        modifier = modifier.padding(16.dp),
    ) {
        PriceChange(
            price = "$${state.price.setScale(2, RoundingMode.HALF_UP)}",
            priceChangeText = priceChangeText,
            priceChangeType = priceChangeType,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
private fun CoinDetailScreenPreview() {
    CoinDetailScreen(
        state = CoinDetailUiState(
            symbol = "BTCUSDT",
            price = BigDecimal("73500.89"),
            priceChangePercentage24h = 2.58,
            isLoading = false,
        ),
        onEvent = {},
        modifier = Modifier.background(ScreenBackground)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
private fun CoinDetailScreenLoadingPreview() {
    CoinDetailScreen(
        state = CoinDetailUiState(isLoading = true),
        onEvent = {},
        modifier = Modifier.background(ScreenBackground)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFF1A1A1A)
@Composable
private fun CoinDetailScreenErrorPreview() {
    CoinDetailScreen(
        state = CoinDetailUiState(
            isLoading = false,
            errorMsg = "Network error occurred"
        ),
        onEvent = {},
        modifier = Modifier.background(ScreenBackground)
    )
}

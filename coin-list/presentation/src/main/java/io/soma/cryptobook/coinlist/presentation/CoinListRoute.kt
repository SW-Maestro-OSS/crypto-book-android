package io.soma.cryptobook.coinlist.presentation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.soma.cryptobook.core.presentation.collectWithLifecycle

@Composable
fun CoinListRoute(
    onNavigateToCoinDetail: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CoinListViewModel = hiltViewModel()
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    viewModel.sideEffect.collectWithLifecycle() { effect ->
        when (effect) {
            is CoinListSideEffect.NavigateToCoinDetail -> onNavigateToCoinDetail(effect.symbol)
            is CoinListSideEffect.ShowToast -> {
                Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
            }

            CoinListSideEffect.Close -> {
                // 뒤로가기 로직 구현
            }
        }
    }

    CoinListScreen(
        state = uiState,
        onEvent = viewModel::handleEvent,
        modifier = modifier
    )
}
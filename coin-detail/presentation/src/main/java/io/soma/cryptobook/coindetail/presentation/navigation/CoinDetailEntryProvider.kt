package io.soma.cryptobook.coindetail.presentation.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import io.soma.cryptobook.coindetail.presentation.CoinDetailRoute
import io.soma.cryptobook.coindetail.presentation.CoinDetailViewModel
import io.soma.cryptobook.coindetail.presentation.CoinDetailViewModel.Factory

fun EntryProviderScope<NavKey>.coinDetailEntry() {
    entry<CoinDetailNavKey> { key ->
        val coinName = key.coinName
        CoinDetailRoute(
            viewModel = hiltViewModel<CoinDetailViewModel, Factory>(
                key = coinName,
            ) { factory ->
                factory.create(coinName)
            },
        )
    }
}

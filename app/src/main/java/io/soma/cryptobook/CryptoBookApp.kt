package io.soma.cryptobook

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.soma.cryptobook.navigation.CbNavHost
import io.soma.cryptobook.navigation.rememberCbNavigator
import io.soma.cryptobook.ui.theme.CryptoBookTheme

@Composable
fun CryptoBookApp() {
    CryptoBookTheme() {
        val navigator = rememberCbNavigator()

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CbNavHost(
                navigator = navigator,
                modifier = Modifier.padding(innerPadding)
            )
        }

    }
}
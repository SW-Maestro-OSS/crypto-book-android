package io.soma.cryptobook.splash.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun UpdateRequiredScreen(modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "업데이트가 필요합니다",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "새로운 버전이 출시되었습니다.\n앱을 업데이트해주세요.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                uriHandler.openUri("https://github.com/SW-Maestro-OSS/crypto-book-android")
            },
        ) {
            Text(text = "업데이트")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun UpdateRequiredScreenPreview() {
    MaterialTheme {
        UpdateRequiredScreen()
    }
}

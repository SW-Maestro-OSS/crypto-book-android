package io.soma.cryptobook.settings.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.soma.cryptobook.settings.domain.model.CurrencyUnit
import io.soma.cryptobook.settings.domain.model.Language

@Composable
fun SettingsRoute(modifier: Modifier = Modifier, viewModel: SettingsViewModel = hiltViewModel()) {
    val state by viewModel.stateFlow.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.sideEffectFlow.collect { sideEffect ->
            when (sideEffect) {
                is SettingsSideEffect.ShowError -> {
                }

                is SettingsSideEffect.ShowToast -> {
                }
            }
        }
    }

    SettingsScreen(
        state = state,
        onEvent = viewModel::onEvent,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsScreen(
    state: SettingsState,
    onEvent: (SettingsEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentLanguage = state.userData?.language ?: Language.ENGLISH
    val currentCurrency = state.userData?.currencyUnit ?: CurrencyUnit.DOLLAR

    Column(
        modifier = modifier.padding(16.dp),
    ) {
        // Language Section
        Text(text = "언어 설정")
        Spacer(modifier = Modifier.height(8.dp))
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            Language.entries.forEachIndexed { index, language ->
                SegmentedButton(
                    selected = currentLanguage == language,
                    onClick = { onEvent(SettingsEvent.SetLanguage(language)) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = Language.entries.size,
                    ),
                ) {
                    Text(
                        text = when (language) {
                            Language.ENGLISH -> "English"
                            Language.KOREAN -> "한국어"
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Currency Section
        Text(text = "통화 설정")
        Spacer(modifier = Modifier.height(8.dp))
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            CurrencyUnit.entries.forEachIndexed { index, currency ->
                SegmentedButton(
                    selected = currentCurrency == currency,
                    onClick = { onEvent(SettingsEvent.SetCurrencyUnit(currency)) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = CurrencyUnit.entries.size,
                    ),
                ) {
                    Text(
                        text = when (currency) {
                            CurrencyUnit.DOLLAR -> "USD ($)"
                            CurrencyUnit.WON -> "KRW (₩)"
                        },
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Temporary Navigation Button
        Button(
            onClick = { onEvent(SettingsEvent.NavigateToHome) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "[임시] 홈으로 이동")
        }
    }
}

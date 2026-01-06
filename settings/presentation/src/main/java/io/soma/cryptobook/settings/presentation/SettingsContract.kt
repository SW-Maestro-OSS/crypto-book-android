package io.soma.cryptobook.settings.presentation

import io.soma.cryptobook.core.domain.model.CurrencyUnit
import io.soma.cryptobook.core.domain.model.Language
import io.soma.cryptobook.core.domain.model.UserData
import io.soma.cryptobook.core.presentation.Event
import io.soma.cryptobook.core.presentation.SideEffect
import io.soma.cryptobook.core.presentation.UiState

data class SettingsUiState(
    val userData: UserData? = null,
    val isLoading: Boolean = true,
) : UiState

sealed interface SettingsSideEffect : SideEffect

sealed interface SettingsEvent : Event {
    data class SetLanguage(val language: Language) : SettingsEvent
    data class SetCurrencyUnit(val currencyUnit: CurrencyUnit) : SettingsEvent
    data object NavigateToHome : SettingsEvent
    data object ShowLoadingMessage : SettingsEvent
    data object ShowSnackbarMessage : SettingsEvent
}

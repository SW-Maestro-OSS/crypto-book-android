package io.soma.cryptobook.settings.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import io.soma.cryptobook.settings.domain.model.CurrencyUnit
import io.soma.cryptobook.settings.domain.model.Language
import io.soma.cryptobook.settings.domain.model.UserData
import io.soma.cryptobook.settings.domain.usecase.GetUserDataUseCase
import io.soma.cryptobook.settings.domain.usecase.SetLanguageUseCase
import io.soma.cryptobook.settings.domain.usecase.SetPriceCurrencyUseCase
import io.soma.cryptobook.settings.presentation.base.MviViewModel
import javax.inject.Inject

data class SettingsState(
    val userData: UserData? = null,
    val isLoading: Boolean = true
)

sealed interface SettingsSideEffect {
    data class ShowError(val message: String) : SettingsSideEffect
    data class ShowToast(val message: String) : SettingsSideEffect
}

sealed interface SettingsEvent {
    data class SetLanguage(val language: Language) : SettingsEvent
    data class SetCurrencyUnit(val currencyUnit: CurrencyUnit) : SettingsEvent
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val setPriceCurrencyUseCase: SetPriceCurrencyUseCase
) : MviViewModel<SettingsState, SettingsSideEffect>(SettingsState()) {
    init {
        intent {
            observeUserData()
        }
    }

    private suspend fun observeUserData() {
        getUserDataUseCase().collect { userData ->
            reduce { copy(userData = userData, isLoading = false) }
        }
    }

    fun onEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SetLanguage -> onLanguageChanged(event.language)
            is SettingsEvent.SetCurrencyUnit -> onPriceCurrencyChanged(event.currencyUnit)
        }
    }

    private fun onLanguageChanged(language: Language) = intent {
        runCatching {
            setLanguageUseCase(language)
        }.onSuccess {
            postSideEffect(SettingsSideEffect.ShowToast("언어가 변경되었습니다"))
        }.onFailure {
            postSideEffect(SettingsSideEffect.ShowError("언어 설정 실패"))
        }
    }

    private fun onPriceCurrencyChanged(currencyUnit: CurrencyUnit) = intent {
        runCatching {
            setPriceCurrencyUseCase(currencyUnit)
        }.onSuccess {
            postSideEffect(SettingsSideEffect.ShowToast("통화 설정 성공"))
        }.onFailure {
            postSideEffect(SettingsSideEffect.ShowError("통화 설정 실패"))
        }
    }
}
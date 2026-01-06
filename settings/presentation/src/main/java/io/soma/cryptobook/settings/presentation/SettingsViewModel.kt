package io.soma.cryptobook.settings.presentation

import dagger.hilt.android.lifecycle.HiltViewModel
import io.soma.cryptobook.core.domain.message.MessageHelper
import io.soma.cryptobook.core.domain.model.CurrencyUnit
import io.soma.cryptobook.core.domain.model.Language
import io.soma.cryptobook.core.domain.navigation.AppPage
import io.soma.cryptobook.core.domain.navigation.NavigationHelper
import io.soma.cryptobook.core.domain.usecase.GetUserDataUseCase
import io.soma.cryptobook.core.presentation.MviViewModel
import io.soma.cryptobook.settings.domain.usecase.SetLanguageUseCase
import io.soma.cryptobook.settings.domain.usecase.SetPriceCurrencyUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val navigationHelper: NavigationHelper,
    private val messageHelper: MessageHelper,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val setLanguageUseCase: SetLanguageUseCase,
    private val setPriceCurrencyUseCase: SetPriceCurrencyUseCase,
) : MviViewModel<SettingsEvent, SettingsUiState, SettingsSideEffect>(SettingsUiState()) {
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

    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.SetLanguage -> onLanguageChanged(event.language)
            is SettingsEvent.SetCurrencyUnit -> onPriceCurrencyChanged(event.currencyUnit)
            is SettingsEvent.NavigateToHome -> navigateToHome()
            is SettingsEvent.ShowLoadingMessage -> showLoadingMessage()
            is SettingsEvent.ShowSnackbarMessage -> showSnackbarMessage()
        }
    }

    private fun onLanguageChanged(language: Language) = intent {
        setLanguageUseCase(language)
    }

    private fun onPriceCurrencyChanged(currencyUnit: CurrencyUnit) = intent {
        setPriceCurrencyUseCase(currencyUnit)
    }

    private fun navigateToHome() = intent {
        navigationHelper.navigate(AppPage.Home)
    }

    private fun showLoadingMessage() = intent {
        messageHelper.showLoading()
        delay(3000L)
        messageHelper.hideLoading()
    }

    private fun showSnackbarMessage() {
        messageHelper.showSnackbar(
            message = "스낵바 테스트 메시지입니다",
            actionLabel = "확인",
            onAction = { },
        )
    }
}

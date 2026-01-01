package io.soma.cryptobook.splash.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import io.soma.cryptobook.core.domain.repository.CoinRepository
import io.soma.cryptobook.splash.domain.usecase.CheckUpdateRequirementUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    // hs: versionName을 얻기 위해 사용했지만 맞는건지는 의문임
    @ApplicationContext private val context: Context,
    private val checkUpdateRequirementUseCase: CheckUpdateRequirementUseCase,
    private val coinRepository: CoinRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<SplashUiState>(SplashUiState.Loading)
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val currentVersion = context.packageManager
                .getPackageInfo(context.packageName, 0)
                .versionName.orEmpty()

            val versionCheckJob = async {
                runCatching {
                    checkUpdateRequirementUseCase(currentVersion)
                }.getOrDefault(false)
            }
            val prefetchJob = async {
                runCatching { coinRepository.getCoinPrices() }
            }
            val delayJob = async { delay(2000) }

            val isUpdateRequired = versionCheckJob.await()
            prefetchJob.await()
            delayJob.await()
            _uiState.value = SplashUiState.Success(isUpdateRequired = isUpdateRequired)
        }
    }
}

sealed interface SplashUiState {
    data object Loading : SplashUiState
    data class Success(val isUpdateRequired: Boolean) : SplashUiState

    fun shouldKeepSplashScreen() = this is Loading
    fun shouldNavigateToUpdate() = this is Success && isUpdateRequired
}

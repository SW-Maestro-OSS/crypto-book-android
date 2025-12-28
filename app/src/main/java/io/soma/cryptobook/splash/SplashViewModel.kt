package io.soma.cryptobook.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.soma.cryptobook.core.domain.repository.CoinRepository
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val coinRepository: CoinRepository,
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    init {
        viewModelScope.launch {
            val prefetchJob = async {
                runCatching { coinRepository.getCoinPrices() }
            }
            val delayJob = async { delay(2000) }

            prefetchJob.await()
            delayJob.await()

            _isReady.value = true
        }
    }
}

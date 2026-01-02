package io.soma.cryptobook.core.domain.usecase

import io.soma.cryptobook.core.domain.repository.ExchangeRateRepository
import io.soma.cryptobook.core.domain.repository.UserDataRepository
import javax.inject.Inject

class RefreshExchangeRateUseCase @Inject constructor(
    private val exchangeRateRepository: ExchangeRateRepository,
    private val userDataRepository: UserDataRepository,
) {
    suspend operator fun invoke() {
        val currentUsdKrwExchangeRate = exchangeRateRepository.getUsdKrwExchangeRate()
        userDataRepository.setUsdKrwExchangeRate(currentUsdKrwExchangeRate)
    }
}

package io.soma.cryptobook.settings.domain.usecase

import io.soma.cryptobook.settings.domain.model.CurrencyUnit
import io.soma.cryptobook.settings.domain.repository.UserDataRepository
import javax.inject.Inject

class SetPriceCurrencyUseCase @Inject constructor(
    private val userDataRepository: UserDataRepository
) {
    suspend operator fun invoke(currencyUnit: CurrencyUnit) {
        userDataRepository.setPriceCurrency(currencyUnit)
    }
}
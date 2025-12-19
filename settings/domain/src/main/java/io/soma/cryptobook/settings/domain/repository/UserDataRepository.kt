package io.soma.cryptobook.settings.domain.repository

import io.soma.cryptobook.settings.domain.model.CurrencyUnit
import io.soma.cryptobook.settings.domain.model.Language
import io.soma.cryptobook.settings.domain.model.UserData
import kotlinx.coroutines.flow.Flow

interface UserDataRepository {
    val userData: Flow<UserData>

    suspend fun setLanguage(language: Language)

    suspend fun setPriceCurrency(currencyUnit: CurrencyUnit)
}
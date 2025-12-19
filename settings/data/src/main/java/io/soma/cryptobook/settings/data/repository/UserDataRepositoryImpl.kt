package io.soma.cryptobook.settings.data.repository

import io.soma.cryptobook.settings.data.datastore.CbPreferencesDataSource
import io.soma.cryptobook.settings.domain.model.CurrencyUnit
import io.soma.cryptobook.settings.domain.model.Language
import io.soma.cryptobook.settings.domain.model.UserData
import io.soma.cryptobook.settings.domain.repository.UserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepositoryImpl @Inject constructor(
    private val cbPreferencesDataSource: CbPreferencesDataSource,
) : UserDataRepository {

    override val userData: Flow<UserData> =
        cbPreferencesDataSource.userData

    override suspend fun setLanguage(language: Language) {
        cbPreferencesDataSource.setLanguage(language)
    }

    override suspend fun setPriceCurrency(currencyUnit: CurrencyUnit) {
        cbPreferencesDataSource.setCurrencyUnit(currencyUnit)
    }

}
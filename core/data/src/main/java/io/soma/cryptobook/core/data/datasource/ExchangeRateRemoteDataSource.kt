package io.soma.cryptobook.core.data.datasource

import io.soma.cryptobook.core.data.model.ExchangeRateDto
import io.soma.cryptobook.core.data.network.ExchangeApiService
import io.soma.cryptobook.core.network.base.BaseDataSource
import javax.inject.Inject

class ExchangeRateRemoteDataSource @Inject constructor(
    private val exchangeRateApiService: ExchangeApiService,
) : BaseDataSource() {
    suspend fun getAllExchangeRates(authKey: String, searchDate: String): List<ExchangeRateDto> =
        checkResponse(
            exchangeRateApiService.getExchangeRates(
                authKey = authKey,
                searchDate = searchDate,
            ),
        )
}

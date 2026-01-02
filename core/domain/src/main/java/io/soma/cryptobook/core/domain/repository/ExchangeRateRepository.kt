package io.soma.cryptobook.core.domain.repository

interface ExchangeRateRepository {
    suspend fun getUsdKrwExchangeRate(): Long
}

package io.soma.cryptobook.core.data.repository

import io.soma.cryptobook.core.data.BuildConfig
import io.soma.cryptobook.core.data.datasource.ExchangeRateRemoteDataSource
import io.soma.cryptobook.core.domain.repository.ExchangeRateRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class ExchangeRateRepositoryImpl @Inject constructor(
    private val exchangeRateRemoteDataSource: ExchangeRateRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher,
) : ExchangeRateRepository {
    override suspend fun getUsdKrwExchangeRate(): Long = withContext(ioDispatcher) {
        val authKey = BuildConfig.EXCHANGE_API_KEY
        val dateFormat = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        val calendar = Calendar.getInstance()

        // 한국수출입은행 환율 API
        // https://www.koreaexim.go.kr/ir/HPHKIR020M01?apino=2&viewtype=C&searchselect=&searchword=
        // 이용시 유의사항: 비영업일의 데이터, 혹은 영업당일 11시 이전에 해당일의 데이터를 요청할 경우 null 값이 반환
        // 따라서 데이터를 찾지 못하면 하루씩 이전 날짜로 재시도
        repeat(MAX_RETRY_DAYS) {
            val searchDate = dateFormat.format(calendar.time)

            val exchangeRates = exchangeRateRemoteDataSource.getAllExchangeRates(
                authKey = authKey,
                searchDate = searchDate,
            )

            val dealBasR = exchangeRates
                .find { it.curUnit == "USD" }?.dealBasR

            if (dealBasR != null) {
                return@withContext dealBasR
                    .replace(",", "")
                    .toBigDecimal()
                    .multiply(BigDecimal(10000))
                    .toLong()
            }

            calendar.add(Calendar.DAY_OF_MONTH, -1)
        }
        throw IllegalStateException("USD exchange rate not found for the last $MAX_RETRY_DAYS days")
    }

    companion object {
        private const val MAX_RETRY_DAYS = 7
    }
}

package io.soma.cryptobook.core.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExchangeRateDto(
    val result: Int,
    @SerialName("cur_unit")
    val curUnit: String,
    val ttb: String,
    val tts: String,
    @SerialName("deal_bas_r")
    val dealBasR: String,
    val bkpr: String,
    @SerialName("yy_efee_r")
    val yyEfeeR: String,
    @SerialName("ten_dd_efee_r")
    val tenDdEfeeR: String,
    @SerialName("kftc_bkpr")
    val kftcBkpr: String,
    @SerialName("kftc_deal_bas_r")
    val kftcDealBasR: String,
    @SerialName("cur_nm")
    val curNm: String,
)
